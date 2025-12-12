package com.alvaro.wineselector.rules;

import com.alvaro.wineselector.model.enums.WineProfile;

import java.util.Map;

/**
 * Interface que define o contrato para todas as regras de pontuação.
 * 
 * Cada dimensão de análise (prato, ocasião, intimidade) implementa esta interface,
 * fornecendo um mapa de pontos para cada perfil de vinho baseado em um valor específico.
 * 
 * O sistema soma todas as pontuações e escolhe o perfil vencedor.
 * 
 * @param <T> Tipo do enum que esta regra processa (MainDish, Occasion, ou IntimacyLevel)
 */
public interface ScoringRules<T extends Enum<T>> {

    /**
     * Retorna um mapa com a pontuação de cada perfil de vinho para um valor específico.
     * 
     * A pontuação reflete o quão bem cada perfil harmoniza/combina com o valor fornecido:
     * - Pontuação alta (30-50): Excelente combinação
     * - Pontuação média (15-25): Boa combinação
     * - Pontuação baixa (5-10): Aceitável, mas não ideal
     * - Zero ou ausente: Não recomendado
     * 
     * @param value Valor do enum (ex: MainDish.CARNES_VERMELHAS, Occasion.JANTAR_ROMANTICO)
     * @return Mapa onde a chave é o WineProfile e o valor é a pontuação (0-50)
     * @throws IllegalArgumentException se o valor for null ou não suportado
     */
    Map<WineProfile, Integer> getScores(T value);

    /**
     * Retorna o peso desta regra no cálculo final.
     * 
     * Os pesos padrão recomendados são:
     * - Prato principal: 50% (maior impacto na harmonização)
     * - Ocasião: 30% (contexto social)
     * - Intimidade: 20% (risco/segurança)
     * 
     * O sistema multiplica a pontuação pelo peso para obter o valor final.
     * 
     * @return Peso da regra como decimal (ex: 0.5 para 50%, 0.3 para 30%)
     */
    double getWeight();

    /**
     * Retorna o nome descritivo desta regra para logging e debug.
     * 
     * @return Nome da regra (ex: "Regras de Prato", "Regras de Ocasião")
     */
    String getRuleName();

    /**
     * Valida se um valor é suportado por esta regra.
     * 
     * Implementação padrão que pode ser sobrescrita se necessário.
     * 
     * @param value Valor a ser validado
     * @return true se o valor é válido e suportado
     */
    default boolean isValidValue(T value) {
        return value != null;
    }

    /**
     * Retorna a pontuação de um perfil específico para um valor.
     * Método auxiliar que facilita consultas pontuais.
     * 
     * @param value Valor do enum
     * @param profile Perfil de vinho a consultar
     * @return Pontuação do perfil (0 se não houver pontuação definida)
     */
    default int getScoreForProfile(T value, WineProfile profile) {
        Map<WineProfile, Integer> scores = getScores(value);
        return scores.getOrDefault(profile, 0);
    }
}