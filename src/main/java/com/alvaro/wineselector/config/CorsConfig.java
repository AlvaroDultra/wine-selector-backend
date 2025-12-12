package com.alvaro.wineselector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Configuração de CORS para permitir requisições do frontend.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Permite credenciais (cookies, autenticação)
        config.setAllowCredentials(true);
        
        // Origens permitidas (ajustar para produção)
        config.setAllowedOriginPatterns(List.of(
            "http://localhost:3000",    // React dev
            "http://localhost:5173",    // Vite dev
            "http://localhost:4200"     // Angular dev (futuro)
        ));
        
        // Headers permitidos
        config.setAllowedHeaders(Arrays.asList(
            "Origin",
            "Content-Type",
            "Accept",
            "Authorization",
            "X-Requested-With"
        ));
        
        // Métodos HTTP permitidos
        config.setAllowedMethods(Arrays.asList(
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "OPTIONS"
        ));
        
        // Headers expostos na resposta
        config.setExposedHeaders(Arrays.asList(
            "Content-Type",
            "Authorization"
        ));
        
        // Tempo de cache da configuração CORS (1 hora)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        
        return new CorsFilter(source);
    }
}