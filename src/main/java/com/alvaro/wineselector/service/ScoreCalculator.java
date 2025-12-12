package com.alvaro.wineselector.service;

import com.alvaro.wineselector.model.dto.RecommendationRequest;
import com.alvaro.wineselector.model.enums.WineProfile;
import com.alvaro.wineselector.rules.DishRules;
import com.alvaro.wineselector.rules.IntimacyRules;
import com.alvaro.wineselector.rules.OccasionRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Calculadora de pontuação que orquestra as regras e determina o perfil vencedor.
 * 
 * Responsabilidades:
 * - Aplicar as três dimensões de pontuação (prato, ocasião, intimidade)
 * - Somar pontuações ponderadas pelos respectivos pesos
 * - Determinar perfil recomendado e alternativa
 * - Fornecer transparência no cálculo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreCalculator {

    private final DishRules dishRules;
    private final OccasionRules occasionRules;
    private final IntimacyRules intimacyRules;

    // Diferença mínima para considerar "empate técnico" e sugerir alternativa
    private static final int ALTERNATIVE_THRESHOLD = 10;

    /**
     * Calcula a pontuação final de todos os perfis baseado na requisição.
     * 
     * @param request Requisição com ocasião, intimidade e prato
     * @return Mapa com pontuação final de cada perfil (ordenado decrescente)
     */
    public Map<WineProfile, Double> calculateScores(RecommendationRequest request) {
        log.info("Iniciando cálculo de pontuação para: {}", request);

        // Obter pontuações brutas de cada dimensão
        Map<WineProfile, Integer> dishScores = dishRules.getScores(request.getMainDish());
        Map<WineProfile, Integer> occasionScores = occasionRules.getScores(request.getOccasion());
        Map<WineProfile, Integer> intimacyScores = intimacyRules.getScores(request.getIntimacyLevel());

        // Obter pesos de cada dimensão
        double dishWeight = dishRules.getWeight();
        double occasionWeight = occasionRules.getWeight();
        double intimacyWeight = intimacyRules.getWeight();

        // Calcular pontuação final ponderada para cada perfil
        Map<WineProfile, Double> finalScores = new EnumMap<>(WineProfile.class);

        for (WineProfile profile : WineProfile.values()) {
            double dishScore = dishScores.getOrDefault(profile, 0) * dishWeight;
            double occasionScore = occasionScores.getOrDefault(profile, 0) * occasionWeight;
            double intimacyScore = intimacyScores.getOrDefault(profile, 0) * intimacyWeight;

            double totalScore = dishScore + occasionScore + intimacyScore;
            finalScores.put(profile, totalScore);

            log.debug("Perfil: {} | Prato: {:.2f} | Ocasião: {:.2f} | Intimidade: {:.2f} | Total: {:.2f}",
                    profile.getDisplayName(), dishScore, occasionScore, intimacyScore, totalScore);
        }

        // Ordenar por pontuação (maior para menor)
        return finalScores.entrySet().stream()
                .sorted(Map.Entry.<WineProfile, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Determina o perfil recomendado (maior pontuação).
     * 
     * @param scores Mapa de pontuações finais
     * @return Perfil com maior pontuação
     */
    public WineProfile getRecommendedProfile(Map<WineProfile, Double> scores) {
        return scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException("Nenhum perfil encontrado"));
    }

    /**
     * Determina se há um perfil alternativo válido.
     * Um perfil alternativo é considerado quando:
     * - A diferença para o primeiro lugar é pequena (≤ threshold)
     * - Há valor significativo em oferecer escolha ao usuário
     * 
     * @param scores Mapa de pontuações finais (já ordenado)
     * @return Optional com perfil alternativo, ou vazio se não houver
     */
    public Optional<WineProfile> getAlternativeProfile(Map<WineProfile, Double> scores) {
        List<Map.Entry<WineProfile, Double>> sortedEntries = new ArrayList<>(scores.entrySet());

        if (sortedEntries.size() < 2) {
            return Optional.empty();
        }

        double firstScore = sortedEntries.get(0).getValue();
        double secondScore = sortedEntries.get(1).getValue();
        double difference = firstScore - secondScore;

        log.debug("Diferença entre 1º e 2º lugar: {:.2f} pontos", difference);

        if (difference <= ALTERNATIVE_THRESHOLD) {
            WineProfile alternative = sortedEntries.get(1).getKey();
            log.info("Perfil alternativo encontrado: {} (diferença: {:.2f})", 
                    alternative.getDisplayName(), difference);
            return Optional.of(alternative);
        }

        return Optional.empty();
    }

    /**
     * Obtém a pontuação de um perfil específico.
     * 
     * @param scores Mapa de pontuações
     * @param profile Perfil a consultar
     * @return Pontuação do perfil (arredondada para inteiro)
     */
    public int getScore(Map<WineProfile, Double> scores, WineProfile profile) {
        return (int) Math.round(scores.getOrDefault(profile, 0.0));
    }

    /**
     * Gera um relatório detalhado do cálculo (útil para debug e transparência).
     * 
     * @param request Requisição original
     * @param scores Pontuações finais
     * @return String formatada com detalhes do cálculo
     */
    public String generateCalculationReport(RecommendationRequest request, Map<WineProfile, Double> scores) {
        StringBuilder report = new StringBuilder();
        report.append("========== RELATÓRIO DE CÁLCULO ==========\n");
        report.append(String.format("Prato: %s\n", request.getMainDish().getDisplayName()));
        report.append(String.format("Ocasião: %s\n", request.getOccasion().getDisplayName()));
        report.append(String.format("Intimidade: %s\n\n", request.getIntimacyLevel().getDisplayName()));

        report.append("Pesos aplicados:\n");
        report.append(String.format("- Prato: %.0f%%\n", dishRules.getWeight() * 100));
        report.append(String.format("- Ocasião: %.0f%%\n", occasionRules.getWeight() * 100));
        report.append(String.format("- Intimidade: %.0f%%\n\n", intimacyRules.getWeight() * 100));

        report.append("Pontuação Final:\n");
        scores.forEach((profile, score) -> 
            report.append(String.format("- %s: %.2f pontos\n", profile.getDisplayName(), score))
        );

        report.append("==========================================\n");

        return report.toString();
    }

    /**
     * Calcula a "confiança" da recomendação baseada na diferença entre 1º e 2º lugar.
     * Quanto maior a diferença, maior a confiança.
     * 
     * @param scores Mapa de pontuações finais
     * @return Nível de confiança (0.0 a 1.0)
     */
    public double getConfidenceLevel(Map<WineProfile, Double> scores) {
        List<Map.Entry<WineProfile, Double>> sortedEntries = new ArrayList<>(scores.entrySet());

        if (sortedEntries.size() < 2) {
            return 1.0; // 100% se há apenas uma opção
        }

        double firstScore = sortedEntries.get(0).getValue();
        double secondScore = sortedEntries.get(1).getValue();
        double difference = firstScore - secondScore;

        // Normaliza diferença: 0-5 = baixa, 6-15 = média, 16+ = alta
        if (difference >= 16) return 1.0;      // Alta confiança
        if (difference >= 6) return 0.7;       // Média confiança
        return 0.4;                             // Baixa confiança (empate técnico)
    }
}