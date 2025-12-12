package com.alvaro.wineselector.rules;

import com.alvaro.wineselector.model.enums.Occasion;
import com.alvaro.wineselector.model.enums.WineProfile;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Regras de pontuação baseadas na ocasião social.
 * 
 * Esta dimensão representa o contexto social e a formalidade do evento (peso: 30%).
 * Considera clima esperado, expectativas sociais e adequação do perfil à ocasião.
 * 
 * Pontuação:
 * - 25-30: Perfeitamente adequado à ocasião
 * - 18-22: Muito apropriado
 * - 12-15: Apropriado
 * - 8-10: Aceitável
 * - 0-5: Pouco adequado
 */
@Component
public class OccasionRules implements ScoringRules<Occasion> {

    private static final double WEIGHT = 0.30; // 30% do peso total

    /**
     * Retorna a pontuação de cada perfil de vinho para uma ocasião específica.
     *
     * @param occasion Ocasião social selecionada
     * @return Mapa com pontuação por perfil de vinho
     */
    @Override
    public Map<WineProfile, Integer> getScores(Occasion occasion) {
        if (occasion == null) {
            throw new IllegalArgumentException("Ocasião não pode ser null");
        }

        return switch (occasion) {
            case REUNIAO_NEGOCIOS -> getReuniaoNegociosScores();
            case JANTAR_ROMANTICO -> getJantarRomanticoScores();
            case PRIMEIRO_ENCONTRO -> getPrimeiroEncontroScores();
            case ENTRE_AMIGOS -> getEntreAmigosScores();
            case CELEBRACAO -> getCelebracaoScores();
            case JANTAR_FAMILIA -> getJantarFamiliaScores();
            case CASUAL -> getCasualScores();
        };
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public String getRuleName() {
        return "Regras de Contexto Social";
    }

    // ========================================================================
    // TABELAS DE PONTUAÇÃO POR OCASIÃO
    // ========================================================================

    /**
     * Reunião de Negócios: formal, profissional, escolhas seguras e clássicas
     * Evita ousadia, prioriza elegância discreta
     */
    private Map<WineProfile, Integer> getReuniaoNegociosScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 30);      // Clássico e seguro
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 28); // Elegante e profissional
        scores.put(WineProfile.TINTO_ENCORPADO, 25);  // Sofisticado, mas forte
        scores.put(WineProfile.BRANCO_LEVE, 20);      // Seguro, mas pode parecer simples
        scores.put(WineProfile.ESPUMANTE, 18);        // Apropriado, mas festivo demais
        scores.put(WineProfile.TINTO_LEVE, 15);       // Pode parecer casual demais
        scores.put(WineProfile.ROSE, 10);             // Pouco formal para negócios
        return scores;
    }

    /**
     * Jantar Romântico: elegante, sofisticado, cria atmosfera especial
     * Prioriza perfis que transmitem romance e refinamento
     */
    private Map<WineProfile, Integer> getJantarRomanticoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.ESPUMANTE, 30);        // Romântico e celebratório
        scores.put(WineProfile.TINTO_MEDIO, 28);      // Elegante e sofisticado
        scores.put(WineProfile.TINTO_ENCORPADO, 25);  // Intenso e marcante
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 25); // Refinado
        scores.put(WineProfile.ROSE, 22);             // Romântico e delicado
        scores.put(WineProfile.BRANCO_LEVE, 18);      // Leve demais para o momento
        scores.put(WineProfile.TINTO_LEVE, 18);       // Falta presença
        return scores;
    }

    /**
     * Primeiro Encontro: precisa ser versátil, seguro e agradável
     * Evita extremos, prioriza escolhas que agradam amplamente
     */
    private Map<WineProfile, Integer> getPrimeiroEncontroScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.BRANCO_LEVE, 30);      // Seguro e refrescante
        scores.put(WineProfile.ROSE, 28);             // Versátil e descontraído
        scores.put(WineProfile.TINTO_LEVE, 25);       // Suave e fácil de beber
        scores.put(WineProfile.ESPUMANTE, 25);        // Festivo mas não exagerado
        scores.put(WineProfile.TINTO_MEDIO, 22);      // Bom, mas pode ser intenso
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 20); // Seguro, mas menos versátil
        scores.put(WineProfile.TINTO_ENCORPADO, 12);  // Arriscado demais
        return scores;
    }

    /**
     * Entre Amigos: descontraído, informal, permite ousadia
     * Liberdade para escolhas variadas e divertidas
     */
    private Map<WineProfile, Integer> getEntreAmigosScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.ROSE, 30);             // Descontraído e versátil
        scores.put(WineProfile.TINTO_MEDIO, 28);      // Sempre funciona
        scores.put(WineProfile.TINTO_ENCORPADO, 28);  // Ousadia permitida
        scores.put(WineProfile.ESPUMANTE, 25);        // Festivo e divertido
        scores.put(WineProfile.TINTO_LEVE, 25);       // Fácil de beber
        scores.put(WineProfile.BRANCO_LEVE, 22);      // Refrescante
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 22); // Bom também
        return scores;
    }

    /**
     * Celebração: festivo, alegre, momento especial
     * Espumantes são protagonistas, mas outros perfis também funcionam
     */
    private Map<WineProfile, Integer> getCelebracaoScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.ESPUMANTE, 30);        // Perfeito para celebrar
        scores.put(WineProfile.ROSE, 25);             // Festivo e alegre
        scores.put(WineProfile.TINTO_MEDIO, 22);      // Elegante para brinde
        scores.put(WineProfile.BRANCO_LEVE, 20);      // Refrescante e leve
        scores.put(WineProfile.TINTO_ENCORPADO, 18);  // Pode ser pesado demais
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 18); // Bom, mas menos festivo
        scores.put(WineProfile.TINTO_LEVE, 15);       // Falta o clima festivo
        return scores;
    }

    /**
     * Jantar em Família: confortável, acolhedor, precisa agradar diversos gostos
     * Prioriza versatilidade e facilidade
     */
    private Map<WineProfile, Integer> getJantarFamiliaScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 30);      // Versátil e agrada maioria
        scores.put(WineProfile.TINTO_LEVE, 28);       // Fácil de beber
        scores.put(WineProfile.BRANCO_LEVE, 25);      // Refrescante e seguro
        scores.put(WineProfile.ROSE, 25);             // Versátil e leve
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 22); // Bom, mas específico
        scores.put(WineProfile.ESPUMANTE, 20);        // Bom, mas pode ser muito
        scores.put(WineProfile.TINTO_ENCORPADO, 18);  // Pode não agradar todos
        return scores;
    }

    /**
     * Ocasião Casual: sem pressão, flexível, qualquer escolha é válida
     * Pontuação equilibrada para todos os perfis
     */
    private Map<WineProfile, Integer> getCasualScores() {
        Map<WineProfile, Integer> scores = new EnumMap<>(WineProfile.class);
        scores.put(WineProfile.TINTO_MEDIO, 25);      // Sempre funciona
        scores.put(WineProfile.TINTO_LEVE, 25);       // Descomplicado
        scores.put(WineProfile.BRANCO_LEVE, 25);      // Refrescante
        scores.put(WineProfile.ROSE, 25);             // Descontraído
        scores.put(WineProfile.BRANCO_ESTRUTURADO, 22); // Bom também
        scores.put(WineProfile.TINTO_ENCORPADO, 20);  // Por que não?
        scores.put(WineProfile.ESPUMANTE, 20);        // Pode ser divertido
        return scores;
    }
}