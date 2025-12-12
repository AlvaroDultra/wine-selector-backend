package com.alvaro.wineselector.service;

import com.alvaro.wineselector.model.dto.RecommendationRequest;
import com.alvaro.wineselector.model.enums.IntimacyLevel;
import com.alvaro.wineselector.model.enums.MainDish;
import com.alvaro.wineselector.model.enums.Occasion;
import com.alvaro.wineselector.model.enums.WineProfile;
import org.springframework.stereotype.Service;

/**
 * Gerador de justificativas humanizadas para as recomendações.
 * Explica ao usuário por que determinado perfil foi escolhido.
 */
@Service
public class JustificationGenerator {

    /**
     * Gera justificativa completa baseada na requisição e perfil recomendado.
     */
    public String generateJustification(RecommendationRequest request, WineProfile profile) {
        StringBuilder justification = new StringBuilder();

        // Parte 1: Harmonização com o prato
        justification.append(getDishJustification(request.getMainDish(), profile));
        justification.append(" ");

        // Parte 2: Adequação à ocasião
        justification.append(getOccasionJustification(request.getOccasion(), profile));
        justification.append(" ");

        // Parte 3: Consideração sobre intimidade
        justification.append(getIntimacyJustification(request.getIntimacyLevel(), profile));

        return justification.toString();
    }

    /**
     * Justificativa baseada na harmonização com o prato.
     */
    private String getDishJustification(MainDish dish, WineProfile profile) {
        return switch (dish) {
            case CARNES_VERMELHAS -> 
                profile.name().startsWith("TINTO") 
                    ? "Harmoniza perfeitamente com carnes vermelhas, equilibrando gordura e proteína."
                    : "Oferece um contraste interessante com carnes vermelhas.";

            case CARNES_BRANCAS -> 
                "Combina bem com a versatilidade das carnes brancas, realçando seus sabores delicados.";

            case PEIXES_FRUTOS_MAR -> 
                (profile == WineProfile.BRANCO_LEVE || profile == WineProfile.ESPUMANTE)
                    ? "Harmonização clássica com peixes e frutos do mar, respeitando sabores delicados."
                    : "Oferece uma harmonização interessante com peixes e frutos do mar.";

            case MASSA_MOLHO_VERMELHO -> 
                "Equilibra perfeitamente com a acidez do molho de tomate e a textura da massa.";

            case MASSA_MOLHO_BRANCO -> 
                (profile == WineProfile.BRANCO_ESTRUTURADO)
                    ? "Harmonização ideal para molhos brancos cremosos e queijos."
                    : "Complementa bem a cremosidade e riqueza do molho branco.";

            case RISOTO -> 
                "Acompanha muito bem a cremosidade e complexidade do risoto.";

            case PIZZA -> 
                "Combinação clássica e descontraída, perfeita para pizza.";

            case CHURRASCO -> 
                profile.name().startsWith("TINTO")
                    ? "Escolha perfeita para churrasco, com estrutura para acompanhar carnes grelhadas."
                    : "Oferece um contraste refrescante para o churrasco.";

            case COMIDA_ASIATICA -> 
                "Harmoniza bem com os sabores complexos e delicados da culinária asiática.";

            case QUEIJOS_FRIOS -> 
                "Excelente escolha para tábua de queijos, complementando diversos sabores.";

            case VEGETARIANO -> 
                "Respeita e realça os sabores naturais dos vegetais.";

            case COMIDA_APIMENTADA -> 
                (profile == WineProfile.BRANCO_LEVE || profile == WineProfile.ROSE)
                    ? "O frescor deste vinho contrabalança perfeitamente o picante."
                    : "Oferece equilíbrio interessante com temperos intensos.";
        };
    }

    /**
     * Justificativa baseada na ocasião.
     */
    private String getOccasionJustification(Occasion occasion, WineProfile profile) {
        return switch (occasion) {
            case REUNIAO_NEGOCIOS -> 
                "Adequado para ambiente profissional, transmitindo elegância e sofisticação.";

            case JANTAR_ROMANTICO -> 
                profile == WineProfile.ESPUMANTE
                    ? "Cria uma atmosfera especial e romântica para o jantar."
                    : "Combina com o clima romântico, agregando requinte ao momento.";

            case PRIMEIRO_ENCONTRO -> 
                "Escolha segura e versátil, ideal para um primeiro encontro.";

            case ENTRE_AMIGOS -> 
                "Perfeito para o clima descontraído entre amigos.";

            case CELEBRACAO -> 
                profile == WineProfile.ESPUMANTE
                    ? "A escolha clássica para celebrar momentos especiais!"
                    : "Adequado para celebração, trazendo alegria ao momento.";

            case JANTAR_FAMILIA -> 
                "Agrada diversos paladares em uma reunião familiar.";

            case CASUAL -> 
                "Escolha descomplicada para uma ocasião casual.";
        };
    }

    /**
     * Justificativa baseada no nível de intimidade.
     */
    private String getIntimacyJustification(IntimacyLevel intimacy, WineProfile profile) {
        if (intimacy.requiresMaxSafety()) {
            return "É uma escolha segura e amplamente agradável para este nível de intimidade.";
        }

        if (intimacy.allowsBoldChoices()) {
            return profile == WineProfile.TINTO_ENCORPADO || profile == WineProfile.ESPUMANTE
                ? "A intimidade permite uma escolha mais marcante e personalizada."
                : "Escolha confortável que aproveita a liberdade deste nível de intimidade.";
        }

        return "Equilibra bem formalidade e conforto para este contexto.";
    }

    /**
     * Gera uma sugestão de como servir o vinho (temperatura, taça).
     */
    public String getServingSuggestion(WineProfile profile) {
        return switch (profile) {
            case TINTO_ENCORPADO -> 
                "Sirva entre 16-18°C em taça de vinho tinto grande.";
            
            case TINTO_MEDIO, TINTO_LEVE -> 
                "Sirva entre 14-16°C em taça de vinho tinto.";
            
            case BRANCO_ESTRUTURADO -> 
                "Sirva entre 10-12°C em taça de vinho branco.";
            
            case BRANCO_LEVE -> 
                "Sirva bem gelado, entre 8-10°C, em taça de vinho branco.";
            
            case ROSE -> 
                "Sirva gelado, entre 8-10°C, em taça de vinho branco ou rosé.";
            
            case ESPUMANTE -> 
                "Sirva bem gelado, entre 6-8°C, em taça flute.";
        };
    }
}