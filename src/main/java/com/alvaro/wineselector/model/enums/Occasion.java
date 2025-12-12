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

    JANTAR_ROMANTICO(
            "Jantar Romântico",
            "Momento especial a dois, onde elegância e sofisticação são valorizadas."
    ),

    PRIMEIRO_ENCONTRO(
            "Primeiro Encontro",
            "Situação que exige escolhas versáteis e que agradem diferentes paladares."
    ),

    ENTRE_AMIGOS(
            "Entre Amigos",
            "Ambiente descontraído e informal, permitindo escolhas mais ousadas e variadas."
    ),

    CELEBRACAO(
            "Celebração",
            "Momento festivo e alegre, ideal para vinhos que transmitem celebração."
    ),

    JANTAR_FAMILIA(
            "Jantar em Família",
            "Reunião familiar que pede vinhos agradáveis e que agradem a todos."
    ),

    CASUAL(
            "Ocasião Casual",
            "Momento informal e despreocupado, sem exigências específicas."
    );

    // Atributos do enum
    private final String displayName;
    private final String description;

    /**
     * Construtor do enum.
     *
     * @param displayName Nome a ser exibido para o usuário
     * @param description Descrição do contexto da ocasião
     */
    Occasion(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    @JsonValue  // ← ADICIONAR
    public String getName() {
        return this.name();
    }

    /**
     * Retorna uma representação textual amigável da ocasião.
     *
     * @return Nome de exibição da ocasião
     */
    @Override
    public String toString() {
        return displayName;
    }
}