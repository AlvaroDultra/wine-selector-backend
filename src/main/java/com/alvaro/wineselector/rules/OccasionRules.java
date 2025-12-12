package com.alvaro.wineselector.rules;

import com.alvaro.wineselector.model.enums.Occasion;
import com.alvaro.wineselector.model.enums.WineProfile;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Regras de pontuação baseadas na ocasião social.
 */
@Component
public class OccasionRules implements ScoringRules<Occasion> {

    private static final double WEIGHT = 0.30;

    @Override
    public Map<WineProfile, Integer> getScores(Occasion occasion) {
        if (occasion == null) {
            throw new IllegalArgumentException("Ocasião não pode ser null");
        }

        return switch (occasion) {
            case REUNIAO_NEGOCIOS -> getReuniaoNegociosScores();
            case ALMOCO_NEGOCIOS -> getAlmocoNegociosScores();
            case JANTAR_ROMANTICO -> getJantarRomanticoScores();
            case PRIMEIRO_ENCONTRO -> getPrimeiroEncontroScores();
            case ANIVERSARIO -> getAniversarioScores();
            case CELEBRACAO -> getCelebracaoScores();
            case ENTRE_AMIGOS -> getEntreAmigosScores();
            case JANTAR_FAMILIA -> getJantarFamiliaScores();
            case BRUNCH_HAPPY_HOUR -> getBrunchHappyHourScores();
            case CASUAL -> getCasualScores();
        };
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public String getRuleName() {
        return "Regras de Contexto Social";
    }

    private Map<WineProfile, Integer> getReuniaoNegociosScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 30);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 28);
        scores.put(WineProfile.TINTO_ENCORPADO, 25);
        scores.put(WineProfile.BRANCO_LEVE, 20);
        scores.put(WineProfile.ESPUMANTE, 18);
        scores.put(WineProfile.TINTO_LEVE, 15);
        scores.put(WineProfile.ROSE, 10);
        return scores;
    }

    private Map<WineProfile, Integer> getAlmocoNegociosScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 30);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 28);
        scores.put(WineProfile.TINTO_LEVE, 25);
        scores.put(WineProfile.ROSE, 22);
        scores.put(WineProfile.TINTO_MEDIO, 20);
        scores.put(WineProfile.ESPUMANTE, 15);
        scores.put(WineProfile.TINTO_ENCORPADO, 12);
        return scores;
    }

    private Map<WineProfile, Integer> getJantarRomanticoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.ESPUMANTE, 30);
        scores.put(WineProfile.TINTO_MEDIO, 28);
        scores.put(WineProfile.TINTO_ENCORPADO, 25);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 25);
        scores.put(WineProfile.ROSE, 22);
        scores.put(WineProfile.BRANCO_LEVE, 18);
        scores.put(WineProfile.TINTO_LEVE, 18);
        return scores;
    }

    private Map<WineProfile, Integer> getPrimeiroEncontroScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 30);
        scores.put(WineProfile.ROSE, 28);
        scores.put(WineProfile.TINTO_LEVE, 25);
        scores.put(WineProfile.ESPUMANTE, 25);
        scores.put(WineProfile.TINTO_MEDIO, 22);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 20);
        scores.put(WineProfile.TINTO_ENCORPADO, 12);
        return scores;
    }

    private Map<WineProfile, Integer> getAniversarioScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.ESPUMANTE, 30);
        scores.put(WineProfile.TINTO_ENCORPADO, 25);
        scores.put(WineProfile.TINTO_MEDIO, 25);
        scores.put(WineProfile.ROSE, 22);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 20);
        scores.put(WineProfile.BRANCO_LEVE, 18);
        scores.put(WineProfile.TINTO_LEVE, 18);
        return scores;
    }

    private Map<WineProfile, Integer> getCelebracaoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.ESPUMANTE, 30);
        scores.put(WineProfile.ROSE, 25);
        scores.put(WineProfile.TINTO_MEDIO, 22);
        scores.put(WineProfile.BRANCO_LEVE, 20);
        scores.put(WineProfile.TINTO_ENCORPADO, 18);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 18);
        scores.put(WineProfile.TINTO_LEVE, 15);
        return scores;
    }

    private Map<WineProfile, Integer> getEntreAmigosScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.ROSE, 30);
        scores.put(WineProfile.TINTO_MEDIO, 28);
        scores.put(WineProfile.TINTO_ENCORPADO, 28);
        scores.put(WineProfile.ESPUMANTE, 25);
        scores.put(WineProfile.TINTO_LEVE, 25);
        scores.put(WineProfile.BRANCO_LEVE, 22);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 22);
        return scores;
    }

    private Map<WineProfile, Integer> getJantarFamiliaScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 30);
        scores.put(WineProfile.TINTO_LEVE, 28);
        scores.put(WineProfile.BRANCO_LEVE, 25);
        scores.put(WineProfile.ROSE, 25);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 22);
        scores.put(WineProfile.ESPUMANTE, 20);
        scores.put(WineProfile.TINTO_ENCORPADO, 18);
        return scores;
    }

    private Map<WineProfile, Integer> getBrunchHappyHourScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 30);
        scores.put(WineProfile.ROSE, 30);
        scores.put(WineProfile.ESPUMANTE, 28);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 20);
        scores.put(WineProfile.TINTO_LEVE, 15);
        scores.put(WineProfile.TINTO_MEDIO, 10);
        scores.put(WineProfile.TINTO_ENCORPADO, 5);
        return scores;
    }

    private Map<WineProfile, Integer> getCasualScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 25);
        scores.put(WineProfile.TINTO_LEVE, 25);
        scores.put(WineProfile.BRANCO_LEVE, 25);
        scores.put(WineProfile.ROSE, 25);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 22);
        scores.put(WineProfile.TINTO_ENCORPADO, 20);
        scores.put(WineProfile.ESPUMANTE, 20);
        return scores;
    }
}