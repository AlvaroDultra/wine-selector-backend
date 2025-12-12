package com.alvaro.wineselector.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Pratos principais que serão servidos na ocasião.
 * O prato é a dimensão mais importante para harmonização (peso: 50% na pontuação).
 */
@Getter
public enum MainDish {

    CARNES_VERMELHAS(
            "Carnes Vermelhas",
            "Bife, costela, picanha, cordeiro e outras carnes vermelhas grelhadas ou assadas.",
            "red_meat"
    ),

    CARNES_BRANCAS(
            "Carnes Brancas",
            "Frango, peru, chester e outras aves preparadas de diversas formas.",
            "white_meat"
    ),

    PEIXES_FRUTOS_MAR(
            "Peixes e Frutos do Mar",
            "Salmão, tilápia, camarão, polvo, lula e outros pescados frescos.",
            "seafood"
    ),

    MASSA_MOLHO_VERMELHO(
            "Massa com Molho Vermelho",
            "Massas italianas com molho de tomate, bolonhesa, arrabiata ou pomodoro.",
            "pasta_red"
    ),

    MASSA_MOLHO_BRANCO(
            "Massa com Molho Branco",
            "Massas com molho alfredo, quatro queijos, carbonara ou ao pesto.",
            "pasta_white"
    ),

    RISOTO(
            "Risoto",
            "Risotos cremosos de funghi, camarão, frango, limão siciliano ou outros.",
            "risotto"
    ),

    PIZZA(
            "Pizza",
            "Pizzas de diversos sabores, desde margherita até as mais incrementadas.",
            "pizza"
    ),

    CHURRASCO(
            "Churrasco",
            "Variedade de carnes grelhadas no estilo brasileiro, incluindo linguiça e frango.",
            "bbq"
    ),

    COMIDA_ASIATICA(
            "Comida Asiática",
            "Sushi, sashimi, yakisoba, pratos tailandeses, chineses ou japoneses.",
            "asian"
    ),

    QUEIJOS_FRIOS(
            "Tábua de Queijos e Frios",
            "Seleção de queijos, embutidos, azeitonas e acompanhamentos.",
            "cheese_board"
    ),

    VEGETARIANO(
            "Prato Vegetariano",
            "Legumes grelhados, saladas substanciais, quiches, tortas ou pratos à base de vegetais.",
            "vegetarian"
    ),

    COMIDA_APIMENTADA(
            "Comida Apimentada",
            "Pratos com temperos marcantes e picantes, como comida mexicana ou indiana.",
            "spicy"
    );

    // Atributos do enum
    private final String displayName;
    private final String description;
    private final String category; // Categoria técnica para lógica de harmonização

    /**
     * Construtor do enum.
     *
     * @param displayName Nome a ser exibido para o usuário
     * @param description Descrição e exemplos do prato
     * @param category Categoria técnica para regras de harmonização
     */
    MainDish(String displayName, String description, String category) {
        this.displayName = displayName;
        this.description = description;
        this.category = category;
    }

    @JsonValue  // ← ADICIONAR
    public String getName() {
        return this.name();
    }

    /**
     * Verifica se o prato é baseado em carne vermelha.
     *
     * @return true se for carne vermelha ou churrasco
     */
    public boolean isRedMeatBased() {
        return this == CARNES_VERMELHAS || this == CHURRASCO;
    }

    /**
     * Verifica se o prato é leve e delicado.
     *
     * @return true se for peixe, frutos do mar ou vegetariano
     */
    public boolean isLightDish() {
        return this == PEIXES_FRUTOS_MAR || 
               this == VEGETARIANO || 
               this == COMIDA_ASIATICA;
    }

    /**
     * Verifica se o prato tem base de massas ou carboidratos.
     *
     * @return true se for massa, pizza ou risoto
     */
    public boolean isPastaBased() {
        return this == MASSA_MOLHO_VERMELHO || 
               this == MASSA_MOLHO_BRANCO || 
               this == PIZZA || 
               this == RISOTO;
    }

    /**
     * Verifica se o prato é intenso e marcante.
     *
     * @return true se for apimentado ou churrasco
     */
    public boolean isIntenseFlavored() {
        return this == COMIDA_APIMENTADA || this == CHURRASCO;
    }

    /**
     * Retorna uma representação textual amigável do prato.
     *
     * @return Nome de exibição do prato
     */
    @Override
    public String toString() {
        return displayName;
    }
}