package com.alvaro.wineselector.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Nível de intimidade com as pessoas presentes na ocasião.
 * Determina o grau de risco social e a necessidade de escolhas seguras versus ousadas.
 */
@Getter
public enum IntimacyLevel {

    PRIMEIRO_ENCONTRO(
            "Primeiro Encontro",
            "Situação de baixa intimidade que exige máxima segurança na escolha. " +
            "Prioriza vinhos versáteis e amplamente agradáveis.",
            1 // Nível de risco: mínimo
    ),

    CONHECIDO(
            "Conhecido",
            "Relação superficial que ainda requer escolhas conservadoras e seguras. " +
            "Alguma margem para escolhas mais interessantes.",
            2 // Nível de risco: baixo
    ),

    COLEGA_TRABALHO(
            "Colega de Trabalho",
            "Relação profissional que permite escolhas equilibradas, mas mantém formalidade. " +
            "Boa margem para vinhos clássicos e elegantes.",
            3 // Nível de risco: moderado
    ),

    AMIGO_PROXIMO(
            "Amigo Próximo",
            "Alta intimidade que permite escolhas mais ousadas e personalizadas. " +
            "Conforto para experimentar e arriscar.",
            4 // Nível de risco: alto
    ),

    INTIMO_FAMILIAR(
            "Íntimo/Familiar",
            "Máxima intimidade onde há total liberdade de escolha. " +
            "Qualquer perfil é válido, priorizando apenas a harmonização.",
            5 // Nível de risco: máximo
    );

    // Atributos do enum
    private final String displayName;
    private final String description;
    private final int riskTolerance; // 1 (baixo) a 5 (alto)

    /**
     * Construtor do enum.
     *
     * @param displayName Nome a ser exibido para o usuário
     * @param description Descrição do nível de intimidade
     * @param riskTolerance Nível de tolerância a risco (1-5)
     */
    IntimacyLevel(String displayName, String description, int riskTolerance) {
        this.displayName = displayName;
        this.description = description;
        this.riskTolerance = riskTolerance;
    }

    @JsonValue  // ← ADICIONAR
    public String getName() {
        return this.name();
    }

    /**
     * Verifica se este nível permite escolhas ousadas.
     *
     * @return true se o nível de risco for alto (≥ 4)
     */
    public boolean allowsBoldChoices() {
        return riskTolerance >= 4;
    }

    /**
     * Verifica se este nível exige máxima segurança.
     *
     * @return true se o nível de risco for mínimo (≤ 2)
     */
    public boolean requiresMaxSafety() {
        return riskTolerance <= 2;
    }

    /**
     * Retorna uma representação textual amigável do nível.
     *
     * @return Nome de exibição do nível de intimidade
     */
    @Override
    public String toString() {
        return displayName;
    }
}