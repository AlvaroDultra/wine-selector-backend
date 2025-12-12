package com.alvaro.wineselector.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Perfis de vinho que o sistema pode recomendar.
 * Cada perfil representa uma categoria ampla de vinhos com características similares.
 */
@Getter
public enum WineProfile {



    TINTO_LEVE(
            "Tinto Leve",
            "Vinho tinto com taninos suaves, corpo leve e fácil de beber. " +
            "Ideal para quem busca algo menos intenso e mais versátil."
    ),

    TINTO_MEDIO(
            "Tinto Médio",
            "Vinho tinto equilibrado com boa estrutura e taninos moderados. " +
            "A escolha mais versátil e segura para diversas ocasiões."
    ),

    TINTO_ENCORPADO(
            "Tinto Encorpado",
            "Vinho tinto intenso, com taninos marcantes e grande estrutura. " +
            "Para quem aprecia vinhos mais robustos e com personalidade forte."
    ),

    BRANCO_LEVE(
            "Branco Leve",
            "Vinho branco fresco, delicado e com boa acidez. " +
            "Perfeito para momentos mais leves e descontraídos."
    ),

    BRANCO_ESTRUTURADO(
            "Branco Estruturado",
            "Vinho branco com mais corpo, complexidade e presença gastronômica. " +
            "Harmoniza bem com pratos mais elaborados."
    ),

    ROSE(
            "Rosé",
            "Vinho rosé versátil, fresco e elegante. " +
            "Excelente para ocasiões informais e clima descontraído."
    ),

    ESPUMANTE(
            "Espumante",
            "Vinho espumante festivo e elegante, com finas bolhas. " +
            "Perfeito para celebrações e momentos especiais."
    );

    // Atributos do enum
    private final String displayName;
    private final String description;

    /**
     * Construtor do enum.
     *
     * @param displayName Nome a ser exibido para o usuário
     * @param description Descrição detalhada do perfil
     */
    WineProfile(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    @JsonValue  // ← ADICIONAR ESTA LINHA
    public String getName() {
        return this.name();
    }

    /**
     * Retorna uma representação textual amigável do perfil.
     *
     * @return Nome de exibição do perfil
     */
    @Override
    public String toString() {
        return displayName;
    }
}