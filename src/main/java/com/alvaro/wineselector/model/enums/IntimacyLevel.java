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
            1
    ),

    CONHECIDO(
            "Conhecido",
            "Relação superficial que ainda requer escolhas conservadoras e seguras. " +
                    "Alguma margem para escolhas mais interessantes.",
            2
    ),

    AMIGO_DISTANTE(
            "Amigo Distante",
            "Amizade estabelecida mas sem grande proximidade no dia a dia. " +
                    "Permite escolhas equilibradas com moderada segurança.",
            3
    ),

    AMIGO(
            "Amigo",
            "Amizade consolidada com conforto moderado para escolhas variadas. " +
                    "Boa margem para vinhos interessantes.",
            4
    ),

    AMIGO_PROXIMO(
            "Amigo Próximo",
            "Alta intimidade que permite escolhas mais ousadas e personalizadas. " +
                    "Conforto para experimentar e arriscar.",
            5
    ),

    AMIGO_REVER(
            "Amigo que Irá Rever",
            "Reencontro com amigo de longa data. Nostalgia e história permitem liberdade. " +
                    "Momento especial que aceita escolhas marcantes.",
            5
    ),

    COLEGA_TRABALHO(
            "Colega de Trabalho",
            "Relação profissional que permite escolhas equilibradas, mas mantém formalidade. " +
                    "Boa margem para vinhos clássicos e elegantes.",
            3
    ),

    CHEFE_SUPERIOR(
            "Chefe/Superior",
            "Contexto hierárquico que exige máxima formalidade e segurança. " +
                    "Escolhas devem ser clássicas e irrepreensíveis.",
            2
    ),

    CLIENTE_FORNECEDOR(
            "Cliente/Fornecedor",
            "Relação comercial que requer elegância, profissionalismo e segurança. " +
                    "Vinhos devem transmitir bom gosto sem ousadia.",
            2
    ),

    INTIMO_FAMILIAR(
            "Íntimo/Familiar",
            "Máxima intimidade onde há total liberdade de escolha. " +
                    "Qualquer perfil é válido, priorizando apenas a harmonização.",
            6
    );

    private final String displayName;
    private final String description;
    private final int riskTolerance;

    IntimacyLevel(String displayName, String description, int riskTolerance) {
        this.displayName = displayName;
        this.description = description;
        this.riskTolerance = riskTolerance;
    }

    @JsonValue
    public String getName() {
        return this.name();
    }

    public boolean allowsBoldChoices() {
        return riskTolerance >= 5;
    }

    public boolean requiresMaxSafety() {
        return riskTolerance <= 2;
    }

    @Override
    public String toString() {
        return displayName;
    }
}