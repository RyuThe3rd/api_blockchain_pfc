package org.example.blockchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RateLimitingFilter rateLimitingFilter;

    public SecurityConfig(RateLimitingFilter rateLimitingFilter) {
        this.rateLimitingFilter = rateLimitingFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Desativa CSRF pois a API é Stateless (REST)
                .csrf(AbstractHttpConfigurer::disable)

                // Regista o filtro de Rate Limiting na cadeia do Spring Security
                .addFilterBefore(rateLimitingFilter, UsernamePasswordAuthenticationFilter.class)

                // Define gestão de sessão como STATELESS (sem cookies no servidor)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configuração de permissões de rotas (Tudo público)
                .authorizeHttpRequests(auth -> auth
                        // 1. Rotas Públicas (Health Check, Actuator, H2)
                        .requestMatchers("/api/v1/health/**", "/actuator/**", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/statements/verify/**").permitAll()

                        // 2. Rotas de Administração e Statements
                        .requestMatchers("/api/v1/admin/**").permitAll()
                        .requestMatchers("/api/v1/statements/**").permitAll()

                        // Qualquer outra rota
                        .anyRequest().permitAll()
                )

                // Desativa caixa de diálogo do HTTP Basic
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}