package com.alvaro.wineselector.controller;

import com.alvaro.wineselector.model.dto.RecommendationRequest;
import com.alvaro.wineselector.model.dto.RecommendationResponse;
import com.alvaro.wineselector.service.RecommendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para recomendação de vinhos.
 * Expõe endpoints para o frontend consumir.
 */
@Slf4j
@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Temporário - ajustar para produção
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * Endpoint principal: retorna recomendação de vinho.
     * 
     * POST /api/recommendation
     * 
     * @param request JSON com ocasião, intimidade e prato
     * @return Resposta com perfil recomendado e justificativa
     */
    @PostMapping
    public ResponseEntity<RecommendationResponse> getRecommendation(
            @Valid @RequestBody RecommendationRequest request) {
        
        log.info("Recebida requisição de recomendação: {}", request);

        RecommendationResponse response = recommendationService.getRecommendation(request);

        log.info("Recomendação gerada: {}", response.getRecommendedProfile().getDisplayName());

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obter relatório detalhado do cálculo (debug/transparência).
     * 
     * POST /api/recommendation/report
     * 
     * @param request JSON com ocasião, intimidade e prato
     * @return Relatório em texto
     */
    @PostMapping("/report")
    public ResponseEntity<String> getCalculationReport(
            @Valid @RequestBody RecommendationRequest request) {
        
        log.info("Gerando relatório para: {}", request);

        String report = recommendationService.getCalculationReport(request);

        return ResponseEntity.ok(report);
    }

    /**
     * Endpoint para obter sugestão de como servir o vinho.
     * 
     * POST /api/recommendation/serving
     * 
     * @param request JSON com ocasião, intimidade e prato
     * @return Dica de temperatura e taça
     */
    @PostMapping("/serving")
    public ResponseEntity<String> getServingSuggestion(
            @Valid @RequestBody RecommendationRequest request) {
        
        log.info("Obtendo sugestão de serviço para: {}", request);

        String suggestion = recommendationService.getServingSuggestion(request);

        return ResponseEntity.ok(suggestion);
    }

    /**
     * Endpoint de health check.
     * 
     * GET /api/recommendation/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Wine Selector API está rodando! 🍷");
    }
}