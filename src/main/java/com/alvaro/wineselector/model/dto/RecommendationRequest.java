package com.alvaro.wineselector.model.dto;

import com.alvaro.wineselector.model.enums.IntimacyLevel;
import com.alvaro.wineselector.model.enums.MainDish;
import com.alvaro.wineselector.model.enums.Occasion;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de requisição para obter recomendação de vinho.
 * Contém as três dimensões necessárias para calcular a recomendação.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {

    /**
     * Ocasião social onde o vinho será servido.
     * Exemplos: JANTAR_ROMANTICO, REUNIAO_NEGOCIOS, ENTRE_AMIGOS
     */
    @NotNull(message = "A ocasião é obrigatória")
    private Occasion occasion;

    /**
     * Nível de intimidade com as pessoas presentes.
     * Exemplos: PRIMEIRO_ENCONTRO, AMIGO_PROXIMO, INTIMO_FAMILIAR
     */
    @NotNull(message = "O nível de intimidade é obrigatório")
    private IntimacyLevel intimacyLevel;

    /**
     * Prato principal que será servido.
     * Exemplos: CARNES_VERMELHAS, PEIXES_FRUTOS_MAR, MASSA_MOLHO_VERMELHO
     */
    @NotNull(message = "O prato principal é obrigatório")
    private MainDish mainDish;

    /**
     * Retorna uma representação textual resumida da requisição.
     *
     * @return String formatada com os valores selecionados
     */
    @Override
    public String toString() {
        return String.format("Requisição[ocasião=%s, intimidade=%s, prato=%s]",
                occasion != null ? occasion.getDisplayName() : "null",
                intimacyLevel != null ? intimacyLevel.getDisplayName() : "null",
                mainDish != null ? mainDish.getDisplayName() : "null");
    }
}