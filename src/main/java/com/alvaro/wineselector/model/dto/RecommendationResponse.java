package com.alvaro.wineselector.model.dto;

import com.alvaro.wineselector.model.enums.WineProfile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de resposta contendo a recomendação de vinho calculada.
 * Inclui o perfil recomendado, justificativa e perfil alternativo quando aplicável.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Não inclui campos null no JSON
public class RecommendationResponse {

    /**
     * Perfil de vinho recomendado (valor do enum).
     * Exemplo: TINTO_MEDIO
     */
    private WineProfile recommendedProfile;

    /**
     * Nome formatado do perfil recomendado para exibição.
     * Exemplo: "Tinto Médio"
     */
    private String displayName;

    /**
     * Descrição detalhada do perfil recomendado.
     * Exemplo: "Vinho tinto equilibrado com boa estrutura e taninos moderados..."
     */
    private String description;

    /**
     * Justificativa da recomendação baseada nas escolhas do usuário.
     * Explica por que este perfil foi escolhido considerando prato, ocasião e intimidade.
     */
    private String justification;

    /**
     * Pontuação final obtida pelo perfil recomendado (para debug/transparência).
     * Exemplo: 85
     */
    private Integer score;

    /**
     * Perfil alternativo quando houver empate técnico ou pontuação próxima.
     * Só é preenchido se a diferença for pequena (≤ 10 pontos).
     */
    private WineProfile alternativeProfile;

    /**
     * Nome formatado do perfil alternativo.
     * Exemplo: "Espumante"
     */
    private String alternativeDisplayName;

    /**
     * Descrição do perfil alternativo.
     */
    private String alternativeDescription;

    /**
     * Pontuação do perfil alternativo (para transparência).
     */
    private Integer alternativeScore;

    /**
     * Cria uma resposta apenas com o perfil principal (sem alternativa).
     *
     * @param profile Perfil de vinho recomendado
     * @param justification Justificativa da escolha
     * @param score Pontuação obtida
     * @return Resposta completa
     */
    public static RecommendationResponse withMainProfile(
            WineProfile profile, 
            String justification, 
            Integer score) {
        
        return RecommendationResponse.builder()
                .recommendedProfile(profile)
                .displayName(profile.getDisplayName())
                .description(profile.getDescription())
                .justification(justification)
                .score(score)
                .build();
    }

    /**
     * Cria uma resposta com perfil principal e alternativo.
     *
     * @param mainProfile Perfil principal recomendado
     * @param mainJustification Justificativa do principal
     * @param mainScore Pontuação do principal
     * @param altProfile Perfil alternativo
     * @param altScore Pontuação do alternativo
     * @return Resposta completa com alternativa
     */
    public static RecommendationResponse withAlternative(
            WineProfile mainProfile,
            String mainJustification,
            Integer mainScore,
            WineProfile altProfile,
            Integer altScore) {
        
        return RecommendationResponse.builder()
                .recommendedProfile(mainProfile)
                .displayName(mainProfile.getDisplayName())
                .description(mainProfile.getDescription())
                .justification(mainJustification)
                .score(mainScore)
                .alternativeProfile(altProfile)
                .alternativeDisplayName(altProfile.getDisplayName())
                .alternativeDescription(altProfile.getDescription())
                .alternativeScore(altScore)
                .build();
    }

    /**
     * Verifica se há perfil alternativo na resposta.
     *
     * @return true se existe perfil alternativo
     */
    public boolean hasAlternative() {
        return alternativeProfile != null;
    }
}