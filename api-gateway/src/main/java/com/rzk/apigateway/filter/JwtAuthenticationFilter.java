package com.rzk.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final SecretKey key;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/register",
            "/api/auth/login"
    );

    private static final Map<HttpMethod, List<String>> ADMIN_ONLY_ROUTES = Map.of(
            HttpMethod.POST, List.of(
                    "/api/catalog/paintings",
                    "/api/exhibitions",
                    "/api/exhibitions/*/paintings/*"
            ),
            HttpMethod.DELETE, List.of(
                    "/api/catalog/paintings/*"
            )
    );

    public JwtAuthenticationFilter(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (PUBLIC_PATHS.contains(path)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            @SuppressWarnings("unchecked")
            List<String> roles = claims.get("roles", List.class);

            if (requiresAdmin(request.getMethod(), path) && (roles == null || !roles.contains("ROLE_ADMIN"))) {
                return forbidden(exchange);
            }

            return chain.filter(exchange);
        } catch (Exception e) {
            return unauthorized(exchange);
        }
    }

    private boolean requiresAdmin(HttpMethod method, String path) {
        List<String> patterns = ADMIN_ONLY_ROUTES.get(method);
        if (patterns == null) return false;
        return patterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}