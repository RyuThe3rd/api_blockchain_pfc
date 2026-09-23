package org.example.blockchain.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Filtro de rate limiting para prevenir abuso de API e ataques DDoS.
 * Usa Bucket4j com algoritmo token bucket para limitar requisições por IP.
 */
@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingFilter.class);

    // Capacidade: 100 requests por minuto por IP
    private static final int CAPACITY = 100;
    // Refill: 100 tokens por minuto (aproximadamente 1.67 tokens por segundo)
    private static final int REFILL_TOKENS = 100;
    private static final Duration REFILL_DURATION = Duration.ofMinutes(1);

    // Cache de buckets por IP
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Não aplica rate limiting a health checks e endpoints de monitoramento
        if (isExemptPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = getClientIP(request);
        Bucket bucket = resolveBucket(clientIp);

        if (bucket.tryConsume(1)) {
            // Adiciona headers de rate limit para transparência
            long availableTokens = bucket.getAvailableTokens();
            response.setHeader("X-RateLimit-Limit", String.valueOf(CAPACITY));
            response.setHeader("X-RateLimit-Remaining", String.valueOf(availableTokens));

            filterChain.doFilter(request, response);
        } else {
            logger.warn("Rate limit exceeded for IP: {}", clientIp);
            response.setStatus(429); // Too Many Requests
            response.setHeader("X-RateLimit-Limit", String.valueOf(CAPACITY));
            response.setHeader("X-RateLimit-Remaining", "0");
            response.setHeader("Retry-After", "60"); // Retry after 60 seconds
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Too many requests. Please try again later.\"}");
        }
    }

    private Bucket resolveBucket(String clientIp) {
        return cache.computeIfAbsent(clientIp, k -> createNewBucket());
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(CAPACITY, Refill.intervally(REFILL_TOKENS, REFILL_DURATION));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private String getClientIP(HttpServletRequest request) {
        // Verifica headers de proxy para obter o IP real do cliente
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isEmpty()) {
            return xfHeader.split(",")[0].trim();
        }

        String xrHeader = request.getHeader("X-Real-IP");
        if (xrHeader != null && !xrHeader.isEmpty()) {
            return xrHeader;
        }

        return request.getRemoteAddr();
    }

    private boolean isExemptPath(String path) {
        // Paths isentos de rate limiting
        return path.startsWith("/actuator") ||
                path.startsWith("/api/health") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs");
    }
}