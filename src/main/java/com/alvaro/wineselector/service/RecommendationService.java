package com.alvaro.wineselector.service;

import com.alvaro.wineselector.model.dto.RecommendationRequest;
import com.alvaro.wineselector.model.dto.RecommendationResponse;
import com.alvaro.wineselector.model.enums.WineProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Serviço principal de recomendação.
 * Orquestra o cálculo de pontuação, determinação do vencedor e geração da resposta.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ScoreCalculator scoreCalculator;
    private final JustificationGenerator justificationGenerator;

    /**
     * Processa uma requisição e retorna a recomendação completa.
     * 
     * @param request Requisição com ocasião, intimidade e prato
     * @return Resposta com perfil recomendado, justificativa e alternativa (se houver)
     */
    public RecommendationResponse getRecommendation(RecommendationRequest request) {
        log.info("Processando recomendação para: {}", request);

        // 1. Calcular pontuações
        Map<WineProfile, Double> scores = scoreCalculator.calculateScores(request);

        // 2. Determinar perfil recomendado
        WineProfile recommendedProfile = scoreCalculator.getRecommendedProfile(scores);
        int recommendedScore = scoreCalculator.getScore(scores, recommendedProfile);

        log.info("Perfil recomendado: {} ({} pontos)", 
                recommendedProfile.getDisplayName(), recommendedScore);

        // 3. Gerar justificativa
        String justification = justificationGenerator.generateJustification(request, recommendedProfile);

        // 4. Verificar se há perfil alternativo
        Optional<WineProfile> alternativeProfile = scoreCalculator.getAlternativeProfile(scores);

        // 5. Montar resposta
        if (alternativeProfile.isPresent()) {
            WineProfile alternative = alternativeProfile.get();
            int alternativeScore = scoreCalculator.getScore(scores, alternative);

            log.info("Perfil alternativo: {} ({} pontos)", 
                    alternative.getDisplayName(), alternativeScore);

            return RecommendationResponse.withAlternative(
                    recommendedProfile,
                    justification,
                    recommendedScore,
                    alternative,
                    alternativeScore
            );
        } else {
            return RecommendationResponse.withMainProfile(
                    recommendedProfile,
                    justification,
                    recommendedScore
            );
        }
    }

    /**
     * Gera relatório detalhado do cálculo (para debug/transparência).
     * 
     * @param request Requisição original
     * @return Relatório em formato texto
     */
    public String getCalculationReport(RecommendationRequest request) {
        Map<WineProfile, Double> scores = scoreCalculator.calculateScores(request);
        return scoreCalculator.generateCalculationReport(request, scores);
    }

    /**
     * Obtém sugestão de como servir o vinho recomendado.
     * 
     * @param request Requisição original
     * @return Dica de serviço (temperatura, taça)
     */
    public String getServingSuggestion(RecommendationRequest request) {
        Map<WineProfile, Double> scores = scoreCalculator.calculateScores(request);
        WineProfile recommended = scoreCalculator.getRecommendedProfile(scores);
        return justificationGenerator.getServingSuggestion(recommended);
    }
}