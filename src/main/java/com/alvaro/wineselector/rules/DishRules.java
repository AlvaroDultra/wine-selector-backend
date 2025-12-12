package com.alvaro.wineselector.rules;

import com.alvaro.wineselector.model.enums.MainDish;
import com.alvaro.wineselector.model.enums.WineProfile;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Regras de pontuação baseadas no prato principal.
 * 
 * Esta é a dimensão mais importante da recomendação (peso: 50%).
 * Define como cada prato harmoniza tecnicamente com cada perfil de vinho.
 * 
 * Pontuação:
 * - 45-50: Harmonização clássica/perfeita
 * - 30-40: Harmonização muito boa
 * - 20-25: Harmonização boa/aceitável
 * - 10-15: Harmonização possível mas não ideal
 * - 0-5: Não recomendado
 */
@Component
public class DishRules implements ScoringRules<MainDish> {

    private static final double WEIGHT = 0.50; // 50% do peso total
    
    /**
     * Retorna a pontuação de cada perfil de vinho para um prato específico.
     *
     * @param dish Prato principal selecionado
     * @return Mapa com pontuação por perfil de vinho
     */
    @Override
    public Map<WineProfile, Integer> getScores(MainDish dish) {
        if (dish == null) {
            throw new IllegalArgumentException("Prato não pode ser null");
        }

        return switch (dish) {
            case CARNES_VERMELHAS -> getCarnesVermelhasScores();
            case CARNES_BRANCAS -> getCarnesBrancasScores();
            case PEIXES_FRUTOS_MAR -> getPeixesFrutosMorScores();
            case MASSA_MOLHO_VERMELHO -> getMassaMolhoVermelhoScores();
            case MASSA_MOLHO_BRANCO -> getMassaMolhoBrancoScores();
            case RISOTO -> getRisotoScores();
            case PIZZA -> getPizzaScores();
            case CHURRASCO -> getChurrascoScores();
            case COMIDA_ASIATICA -> getComidaAsiaticaScores();
            case QUEIJOS_FRIOS -> getQueijosFriosScores();
            case VEGETARIANO -> getVegetarianoScores();
            case COMIDA_APIMENTADA -> getComidaApimentadaScores();
        };
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public String getRuleName() {
        return "Regras de Harmonização por Prato";
    }

    // ========================================================================
    // TABELAS DE PONTUAÇÃO POR PRATO
    // ========================================================================

    /**
     * Carnes Vermelhas: bife, picanha, costela, cordeiro
     * Harmonização clássica: tintos com estrutura
     */
    private Map<WineProfile, Integer> getCarnesVermelhasScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_ENCORPADO, 50);  // Harmonização perfeita
        scores.put(WineProfile.TINTO_MEDIO, 40);      // Muito bom
        scores.put(WineProfile.TINTO_LEVE, 25);       // Aceitável
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 10); // Possível, não ideal
        scores.put(WineProfile.BRANCO_LEVE, 0);       // Não recomendado
        scores.put(WineProfile.ROSE, 5);              // Muito fraco
        scores.put(WineProfile.ESPUMANTE, 5);         // Muito fraco
        return scores;
    }

    /**
     * Carnes Brancas: frango, peru, chester
     * Versátil: aceita tintos leves, brancos estruturados e rosés
     */
    private Map<WineProfile, Integer> getCarnesBrancasScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_LEVE, 45);       // Muito bom
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 45); // Muito bom
        scores.put(WineProfile.ROSE, 40);             // Muito bom
        scores.put(WineProfile.TINTO_MEDIO, 30);      // Bom
        scores.put(WineProfile.BRANCO_LEVE, 25);      // Aceitável
        scores.put(WineProfile.ESPUMANTE, 20);        // Aceitável
        scores.put(WineProfile.TINTO_ENCORPADO, 15);  // Pode sobrepor
        return scores;
    }

    /**
     * Peixes e Frutos do Mar: salmão, tilápia, camarão, lula
     * Harmonização clássica: brancos leves e espumantes
     */
    private Map<WineProfile, Integer> getPeixesFrutosMorScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 50);      // Perfeito
        scores.put(WineProfile.ESPUMANTE, 45);        // Excelente
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 35); // Muito bom
        scores.put(WineProfile.ROSE, 30);             // Bom
        scores.put(WineProfile.TINTO_LEVE, 10);       // Possível, mas raro
        scores.put(WineProfile.TINTO_MEDIO, 0);       // Não recomendado
        scores.put(WineProfile.TINTO_ENCORPADO, 0);   // Não recomendado
        return scores;
    }

    /**
     * Massa com Molho Vermelho: bolonhesa, arrabiata, pomodoro
     * Harmonização clássica: tintos leves a médios
     */
    private Map<WineProfile, Integer> getMassaMolhoVermelhoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 50);      // Perfeito
        scores.put(WineProfile.TINTO_LEVE, 45);       // Excelente
        scores.put(WineProfile.TINTO_ENCORPADO, 30);  // Bom, mas pode sobrepor
        scores.put(WineProfile.ROSE, 25);             // Aceitável
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 15); // Possível
        scores.put(WineProfile.BRANCO_LEVE, 5);       // Fraco
        scores.put(WineProfile.ESPUMANTE, 10);        // Possível
        return scores;
    }

    /**
     * Massa com Molho Branco: alfredo, carbonara, quatro queijos
     * Harmonização: brancos estruturados e tintos leves
     */
    private Map<WineProfile, Integer> getMassaMolhoBrancoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 50); // Perfeito
        scores.put(WineProfile.TINTO_LEVE, 40);       // Muito bom
        scores.put(WineProfile.BRANCO_LEVE, 30);      // Bom
        scores.put(WineProfile.ESPUMANTE, 25);        // Aceitável
        scores.put(WineProfile.ROSE, 20);             // Aceitável
        scores.put(WineProfile.TINTO_MEDIO, 15);      // Possível
        scores.put(WineProfile.TINTO_ENCORPADO, 5);   // Pode sobrepor
        return scores;
    }

    /**
     * Risoto: funghi, camarão, limão siciliano
     * Depende do tipo, mas geralmente brancos estruturados
     */
    private Map<WineProfile, Integer> getRisotoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 50); // Excelente
        scores.put(WineProfile.BRANCO_LEVE, 40);      // Muito bom
        scores.put(WineProfile.TINTO_LEVE, 35);       // Bom
        scores.put(WineProfile.ESPUMANTE, 30);        // Bom
        scores.put(WineProfile.ROSE, 25);             // Aceitável
        scores.put(WineProfile.TINTO_MEDIO, 20);      // Aceitável
        scores.put(WineProfile.TINTO_ENCORPADO, 10);  // Possível, depende
        return scores;
    }

    /**
     * Pizza: margherita, calabresa, quatro queijos
     * Versátil: tintos leves a médios, rosés
     */
    private Map<WineProfile, Integer> getPizzaScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 45);      // Muito bom
        scores.put(WineProfile.TINTO_LEVE, 45);       // Muito bom
        scores.put(WineProfile.ROSE, 40);             // Muito bom
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 30); // Bom
        scores.put(WineProfile.ESPUMANTE, 25);        // Aceitável
        scores.put(WineProfile.TINTO_ENCORPADO, 20);  // Aceitável
        scores.put(WineProfile.BRANCO_LEVE, 15);      // Possível
        return scores;
    }

    /**
     * Churrasco: variedade de carnes grelhadas
     * Harmonização: tintos encorpados e médios
     */
    private Map<WineProfile, Integer> getChurrascoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_ENCORPADO, 50);  // Perfeito
        scores.put(WineProfile.TINTO_MEDIO, 45);      // Excelente
        scores.put(WineProfile.TINTO_LEVE, 30);       // Bom
        scores.put(WineProfile.ROSE, 15);             // Possível
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 10); // Fraco
        scores.put(WineProfile.BRANCO_LEVE, 0);       // Não recomendado
        scores.put(WineProfile.ESPUMANTE, 5);         // Muito fraco
        return scores;
    }

    /**
     * Comida Asiática: sushi, yakisoba, pad thai
     * Harmonização: brancos leves, rosés, espumantes
     */
    private Map<WineProfile, Integer> getComidaAsiaticaScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 50);      // Perfeito
        scores.put(WineProfile.ROSE, 45);             // Excelente
        scores.put(WineProfile.ESPUMANTE, 40);        // Muito bom
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 30); // Bom
        scores.put(WineProfile.TINTO_LEVE, 20);       // Aceitável
        scores.put(WineProfile.TINTO_MEDIO, 10);      // Possível
        scores.put(WineProfile.TINTO_ENCORPADO, 0);   // Não recomendado
        return scores;
    }

    /**
     * Tábua de Queijos e Frios
     * Muito versátil: depende dos queijos, mas geralmente todos funcionam
     */
    private Map<WineProfile, Integer> getQueijosFriosScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 45);      // Muito versátil
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 45); // Muito versátil
        scores.put(WineProfile.ESPUMANTE, 40);        // Excelente
        scores.put(WineProfile.TINTO_ENCORPADO, 35);  // Bom
        scores.put(WineProfile.TINTO_LEVE, 35);       // Bom
        scores.put(WineProfile.ROSE, 35);             // Bom
        scores.put(WineProfile.BRANCO_LEVE, 30);      // Bom
        return scores;
    }

    /**
     * Prato Vegetariano: legumes grelhados, saladas, quiches
     * Harmonização: brancos leves, rosés, tintos leves
     */
    private Map<WineProfile, Integer> getVegetarianoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 50);      // Perfeito
        scores.put(WineProfile.ROSE, 45);             // Excelente
        scores.put(WineProfile.TINTO_LEVE, 40);       // Muito bom
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 35); // Bom
        scores.put(WineProfile.ESPUMANTE, 30);        // Bom
        scores.put(WineProfile.TINTO_MEDIO, 20);      // Aceitável
        scores.put(WineProfile.TINTO_ENCORPADO, 10);  // Pode sobrepor
        return scores;
    }

    /**
     * Comida Apimentada: mexicana, indiana, tailandesa
     * Harmonização: vinhos frescos que contrabalançam o picante
     */
    private Map<WineProfile, Integer> getComidaApimentadaScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 50);      // Perfeito (frescor)
        scores.put(WineProfile.ROSE, 45);             // Excelente (frescor)
        scores.put(WineProfile.ESPUMANTE, 40);        // Muito bom (contraste)
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 30); // Bom
        scores.put(WineProfile.TINTO_LEVE, 20);       // Aceitável
        scores.put(WineProfile.TINTO_MEDIO, 10);      // Possível, mas arriscado
        scores.put(WineProfile.TINTO_ENCORPADO, 0);   // Não recomendado (conflito)
        return scores;
    }
}