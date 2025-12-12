package com.alvaro.wineselector.rules;

import com.alvaro.wineselector.model.enums.IntimacyLevel;
import com.alvaro.wineselector.model.enums.WineProfile;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Regras de pontuação baseadas no nível de intimidade.
 */
@Component
public class IntimacyRules implements ScoringRules<IntimacyLevel> {

    private static final double WEIGHT = 0.20;

    @Override
    public Map<WineProfile, Integer> getScores(IntimacyLevel intimacyLevel) {
        if (intimacyLevel == null) {
            throw new IllegalArgumentException("Nível de intimidade não pode ser null");
        }

        return switch (intimacyLevel) {
            case PRIMEIRO_ENCONTRO -> getPrimeiroEncontroScores();
            case CONHECIDO -> getConhecidoScores();
            case AMIGO_DISTANTE -> getAmigoDistanteScores();
            case AMIGO -> getAmigoScores();
            case AMIGO_PROXIMO -> getAmigoProximoScores();
            case AMIGO_REVER -> getAmigoReverScores();
            case COLEGA_TRABALHO -> getColegaTrabalhoScores();
            case CHEFE_SUPERIOR -> getChefeSuperiorScores();
            case CLIENTE_FORNECEDOR -> getClienteFornecedorScores();
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

    private Map<WineProfile, Integer> getPrimeiroEncontroScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 20);
        scores.put(WineProfile.ROSE, 18);
        scores.put(WineProfile.TINTO_LEVE, 15);
        scores.put(WineProfile.ESPUMANTE, 15);
        scores.put(WineProfile.TINTO_MEDIO, 12);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 10);
        scores.put(WineProfile.TINTO_ENCORPADO, 3);
        return scores;
    }

    private Map<WineProfile, Integer> getConhecidoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 20);
        scores.put(WineProfile.BRANCO_LEVE, 18);
        scores.put(WineProfile.TINTO_LEVE, 18);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 15);
        scores.put(WineProfile.ROSE, 15);
        scores.put(WineProfile.ESPUMANTE, 15);
        scores.put(WineProfile.TINTO_ENCORPADO, 8);
        return scores;
    }

    private Map<WineProfile, Integer> getAmigoDistanteScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 20);
        scores.put(WineProfile.TINTO_LEVE, 18);
        scores.put(WineProfile.BRANCO_LEVE, 18);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 15);
        scores.put(WineProfile.ROSE, 15);
        scores.put(WineProfile.ESPUMANTE, 15);
        scores.put(WineProfile.TINTO_ENCORPADO, 10);
        return scores;
    }

    private Map<WineProfile, Integer> getAmigoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 20);
        scores.put(WineProfile.TINTO_ENCORPADO, 18);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 18);
        scores.put(WineProfile.TINTO_LEVE, 15);
        scores.put(WineProfile.ROSE, 15);
        scores.put(WineProfile.ESPUMANTE, 15);
        scores.put(WineProfile.BRANCO_LEVE, 12);
        return scores;
    }

    private Map<WineProfile, Integer> getAmigoProximoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_ENCORPADO, 20);
        scores.put(WineProfile.TINTO_MEDIO, 18);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 18);
        scores.put(WineProfile.ESPUMANTE, 15);
        scores.put(WineProfile.ROSE, 15);
        scores.put(WineProfile.TINTO_LEVE, 15);
        scores.put(WineProfile.BRANCO_LEVE, 12);
        return scores;
    }

    private Map<WineProfile, Integer> getAmigoReverScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_ENCORPADO, 20);
        scores.put(WineProfile.ESPUMANTE, 20);
        scores.put(WineProfile.TINTO_MEDIO, 18);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 15);
        scores.put(WineProfile.ROSE, 15);
        scores.put(WineProfile.TINTO_LEVE, 15);
        scores.put(WineProfile.BRANCO_LEVE, 12);
        return scores;
    }

    private Map<WineProfile, Integer> getColegaTrabalhoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 20);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 18);
        scores.put(WineProfile.TINTO_ENCORPADO, 15);
        scores.put(WineProfile.BRANCO_LEVE, 15);
        scores.put(WineProfile.TINTO_LEVE, 15);
        scores.put(WineProfile.ESPUMANTE, 12);
        scores.put(WineProfile.ROSE, 10);
        return scores;
    }

    private Map<WineProfile, Integer> getChefeSuperiorScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 20);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 20);
        scores.put(WineProfile.TINTO_ENCORPADO, 18);
        scores.put(WineProfile.BRANCO_LEVE, 15);
        scores.put(WineProfile.ESPUMANTE, 12);
        scores.put(WineProfile.TINTO_LEVE, 12);
        scores.put(WineProfile.ROSE, 8);
        return scores;
    }

    private Map<WineProfile, Integer> getClienteFornecedorScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 20);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 20);
        scores.put(WineProfile.TINTO_ENCORPADO, 15);
        scores.put(WineProfile.ESPUMANTE, 15);
        scores.put(WineProfile.BRANCO_LEVE, 12);
        scores.put(WineProfile.TINTO_LEVE, 12);
        scores.put(WineProfile.ROSE, 10);
        return scores;
    }

    private Map<WineProfile, Integer> getIntimoFamiliarScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_ENCORPADO, 15);
        scores.put(WineProfile.TINTO_MEDIO, 15);
        scores.put(WineProfile.TINTO_LEVE, 15);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 15);
        scores.put(WineProfile.BRANCO_LEVE, 15);
        scores.put(WineProfile.ROSE, 15);
        scores.put(WineProfile.ESPUMANTE, 15);
        return scores;
    }

    public double getSafetyFactor(IntimacyLevel intimacyLevel) {
        if (intimacyLevel == null) {
            return 1.0;
        }
        return 1.0 - (intimacyLevel.getRiskTolerance() / 6.0);
    }

    public boolean isSafeProfile(WineProfile profile, IntimacyLevel intimacyLevel) {
        int score = getScoreForProfile(intimacyLevel, profile);

        if (intimacyLevel.requiresMaxSafety()) {
            return score >= 15;
        }

        return intimacyLevel.allowsBoldChoices() || score >= 12;
    }
}