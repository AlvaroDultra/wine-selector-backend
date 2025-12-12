package com.alvaro.wineselector.rules;

import com.alvaro.wineselector.model.enums.IntimacyLevel;
import com.alvaro.wineselector.model.enums.WineProfile;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Regras de pontuação baseadas no nível de intimidade.
 * 
 * Esta dimensão regula o risco social da escolha (peso: 20%).
 * Determina se você pode ousar com escolhas marcantes ou precisa jogar seguro
 * com vinhos versáteis e amplamente agradáveis.
 * 
 * Conceito: Quanto maior a intimidade, maior a liberdade de escolha.
 * 
 * Pontuação:
 * - 18-20: Perfeitamente adequado ao nível de intimidade
 * - 12-15: Muito apropriado
 * - 8-10: Apropriado
 * - 5-7: Aceitável
 * - 0-3: Arriscado para este nível
 */
@Component
public class IntimacyRules implements ScoringRules<IntimacyLevel> {

    private static final double WEIGHT = 0.20; // 20% do peso total

    /**
     * Retorna a pontuação de cada perfil de vinho para um nível de intimidade específico.
     *
     * @param intimacyLevel Nível de intimidade selecionado
     * @return Mapa com pontuação por perfil de vinho
     */
    @Override
    public Map<WineProfile, Integer> getScores(IntimacyLevel intimacyLevel) {
        if (intimacyLevel == null) {
            throw new IllegalArgumentException("Nível de intimidade não pode ser null");
        }

        return switch (intimacyLevel) {
            case PRIMEIRO_ENCONTRO -> getPrimeiroEncontroScores();
            case CONHECIDO -> getConhecidoScores();
            case COLEGA_TRABALHO -> getColegaTrabalhoScores();
            case AMIGO_PROXIMO -> getAmigoProximoScores();
            case INTIMO_FAMILIAR -> getIntimoFamiliarScores();
        };
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public String getRuleName() {
        return "Regras de Nível de Intimidade";
    }

    // ========================================================================
    // TABELAS DE PONTUAÇÃO POR NÍVEL DE INTIMIDADE
    // ========================================================================

    /**
     * Primeiro Encontro (riskTolerance = 1)
     * Máxima segurança: prioriza vinhos universalmente agradáveis
     * Evita qualquer escolha que possa polarizar ou desagradar
     */
    private Map<WineProfile, Integer> getPrimeiroEncontroScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 20);      // Máxima segurança
        scores.put(WineProfile.ROSE, 18);             // Versátil e agradável
        scores.put(WineProfile.TINTO_LEVE, 15);       // Suave e fácil
        scores.put(WineProfile.ESPUMANTE, 15);        // Seguro e festivo
        scores.put(WineProfile.TINTO_MEDIO, 12);      // Aceitável, mas começa a arriscar
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 10); // Pode ser específico demais
        scores.put(WineProfile.TINTO_ENCORPADO, 3);   // Alto risco de desagradar
        return scores;
    }

    /**
     * Conhecido (riskTolerance = 2)
     * Alta segurança: ainda conservador, mas com pequena margem
     * Permite escolhas clássicas, mas evita extremos
     */
    private Map<WineProfile, Integer> getConhecidoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 20);      // Clássico e seguro
        scores.put(WineProfile.BRANCO_LEVE, 18);      // Muito seguro
        scores.put(WineProfile.TINTO_LEVE, 18);       // Fácil de agradar
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 15); // Bom e elegante
        scores.put(WineProfile.ROSE, 15);             // Versátil
        scores.put(WineProfile.ESPUMANTE, 15);        // Apropriado
        scores.put(WineProfile.TINTO_ENCORPADO, 8);   // Ainda arriscado
        return scores;
    }

    /**
     * Colega de Trabalho (riskTolerance = 3)
     * Segurança moderada: equilíbrio entre profissionalismo e conforto
     * Permite escolhas mais interessantes, mas mantém formalidade
     */
    private Map<WineProfile, Integer> getColegaTrabalhoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 20);      // Sempre apropriado
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 18); // Elegante e profissional
        scores.put(WineProfile.TINTO_ENCORPADO, 15);  // Começa a ser aceitável
        scores.put(WineProfile.BRANCO_LEVE, 15);      // Seguro
        scores.put(WineProfile.TINTO_LEVE, 15);       // Bom também
        scores.put(WineProfile.ESPUMANTE, 12);        // Possível, mas festivo
        scores.put(WineProfile.ROSE, 10);             // Pode parecer informal
        return scores;
    }

    /**
     * Amigo Próximo (riskTolerance = 4)
     * Alta liberdade: permite escolhas ousadas e marcantes
     * Conforto para experimentar e arriscar
     */
    private Map<WineProfile, Integer> getAmigoProximoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_ENCORPADO, 20);  // Ousadia permitida
        scores.put(WineProfile.TINTO_MEDIO, 18);      // Sempre bom
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 18); // Interessante
        scores.put(WineProfile.ESPUMANTE, 15);        // Divertido
        scores.put(WineProfile.ROSE, 15);             // Descontraído
        scores.put(WineProfile.TINTO_LEVE, 15);       // Bom também
        scores.put(WineProfile.BRANCO_LEVE, 12);      // Pode ser simples demais
        return scores;
    }

    /**
     * Íntimo/Familiar (riskTolerance = 5)
     * Liberdade total: qualquer escolha é válida
     * Pontuação equilibrada - foco total na harmonização técnica
     */
    private Map<WineProfile, Integer> getIntimoFamiliarScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        // Pontuação equilibrada - o foco é 100% na harmonização
        scores.put(WineProfile.TINTO_ENCORPADO, 15);  // Todos igualmente válidos
        scores.put(WineProfile.TINTO_MEDIO, 15);      // Sem restrição social
        scores.put(WineProfile.TINTO_LEVE, 15);       // Liberdade total
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 15); // Qualquer escolha OK
        scores.put(WineProfile.BRANCO_LEVE, 15);      // Intimidade permite tudo
        scores.put(WineProfile.ROSE, 15);             // Sem preconceito
        scores.put(WineProfile.ESPUMANTE, 15);        // Tranquilidade plena
        return scores;
    }

    /**
     * Calcula o fator de segurança baseado no nível de intimidade.
     * Quanto menor a intimidade, maior a necessidade de segurança.
     *
     * @param intimacyLevel Nível de intimidade
     * @return Fator de segurança (0.0 a 1.0)
     */
    public double getSafetyFactor(IntimacyLevel intimacyLevel) {
        if (intimacyLevel == null) {
            return 1.0; // Máxima segurança por padrão
        }
        
        // Inverte o riskTolerance: baixa intimidade = alta segurança necessária
        return 1.0 - (intimacyLevel.getRiskTolerance() / 5.0);
    }

    /**
     * Verifica se um perfil é considerado "seguro" para o nível de intimidade.
     * Perfis seguros são aqueles universalmente agradáveis.
     *
     * @param profile Perfil de vinho
     * @param intimacyLevel Nível de intimidade
     * @return true se o perfil é seguro para este nível
     */
    public boolean isSafeProfile(WineProfile profile, IntimacyLevel intimacyLevel) {
        int score = getScoreForProfile(intimacyLevel, profile);
        
        // Considera seguro se a pontuação for >= 15 para baixa intimidade
        if (intimacyLevel.requiresMaxSafety()) {
            return score >= 15;
        }
        
        // Para alta intimidade, todos são seguros
        return intimacyLevel.allowsBoldChoices() || score >= 12;
    }
}