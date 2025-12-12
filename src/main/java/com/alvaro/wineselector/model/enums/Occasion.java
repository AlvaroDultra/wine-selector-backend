package com.alvaro.wineselector.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Ocasiões sociais onde o vinho será servido.
 * A ocasião influencia o nível de formalidade e o estilo de vinho apropriado.
 */
@Getter
public enum Occasion {

    REUNIAO_NEGOCIOS(
            "Reunião de Negócios",
            "Ambiente profissional e formal, exigindo escolhas seguras e elegantes."
    ),

    ALMOCO_NEGOCIOS(
            "Almoço de Negócios",
            "Contexto profissional mais leve que reunião formal, mas ainda corporativo. " +
                    "Permite vinhos mais frescos e menos intensos."
    ),

    JANTAR_ROMANTICO(
            "Jantar Romântico",
            "Momento especial a dois, onde elegância e sofisticação são valorizadas."
    ),

    PRIMEIRO_ENCONTRO(
            "Primeiro Encontro",
            "Situação que exige escolhas versáteis e que agradem diferentes paladares."
    ),

    ANIVERSARIO(
            "Aniversário",
            "Celebração pessoal e festiva, exige vinhos especiais e memoráveis. " +
                    "Momento para escolhas que marquem a ocasião."
    ),

    CELEBRACAO(
            "Celebração",
            "Momento festivo e alegre, ideal para vinhos que transmitem celebração."
    ),

    ENTRE_AMIGOS(
            "Entre Amigos",
            "Ambiente descontraído e informal, permitindo escolhas mais ousadas e variadas."
    ),

    JANTAR_FAMILIA(
            "Jantar em Família",
            "Reunião familiar que pede vinhos agradáveis e que agradem a todos."
    ),

    BRUNCH_HAPPY_HOUR(
            "Brunch/Happy Hour",
            "Momento descontraído durante o dia ou início da noite. " +
                    "Favorece vinhos leves, frescos e fáceis de beber."
    ),

    CASUAL(
            "Ocasião Casual",
            "Momento informal e despreocupado, sem exigências específicas."
    );

    private final String displayName;
    private final String description;

    Occasion(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    @JsonValue
    public String getName() {
        return this.name();
    }

    @Override
    public String toString() {
        return displayName;
    }
}