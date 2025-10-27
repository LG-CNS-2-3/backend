package com.lgcns.gateway.filter;

import com.lgcns.gateway.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * JWT 인증 글로벌 필터
 * 모든 요청에 대해 JWT 검증을 수행하고, 검증된 사용자 정보를 헤더에 추가
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationGlobalFilter implements GlobalFilter, Ordered {

    private final JwtTokenProvider jwtTokenProvider;

    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/api/v1/members/register",
            "/api/v1/members/login",
            "/api/v1/members/refresh",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-resources",
            "/webjars"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        log.info("Gateway Filter - Request Path: {}", path);

        // 인증 제외 경로 체크
        if (isExcludedPath(path)) {
            log.info("Excluded path, skipping authentication: {}", path);
            return chain.filter(exchange);
        }

        // Authorization 헤더 추출
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header");
            return unauthorized(exchange.getResponse(), "Missing or invalid Authorization header");
        }

        // JWT 토큰 추출
        String token = authHeader.substring(7);

        // JWT 검증 (비동기)
        return jwtTokenProvider.validateToken(token)
                .flatMap(isValid -> {
                    if (!isValid) {
                        log.warn("Invalid or expired token");
                        return unauthorized(exchange.getResponse(), "Invalid or expired token");
                    }

                    try {
                        // memberId 추출
                        Long memberId = jwtTokenProvider.getMemberIdFromToken(token);
                        log.info("JWT validation success - memberId: {}", memberId);

                        // X-User-Id 헤더 추가
                        ServerHttpRequest mutatedRequest = request.mutate()
                                .header("X-User-Id", memberId.toString())
                                .build();

                        // 변경된 요청으로 다음 필터 실행
                        return chain.filter(exchange.mutate()
                                .request(mutatedRequest)
                                .build());
                    } catch (Exception e) {
                        log.error("Error extracting memberId from token", e);
                        return unauthorized(exchange.getResponse(), "Invalid token format");
                    }
                })
                .onErrorResume(e -> {
                    log.error("JWT validation error", e);
                    return unauthorized(exchange.getResponse(), "Authentication failed");
                });
    }

    /**
     * 인증 제외 경로 체크
     */
    private boolean isExcludedPath(String path) {
        return EXCLUDE_PATHS.stream().anyMatch(path::startsWith);
    }

    /**
     * 401 Unauthorized 응답 반환
     */
    private Mono<Void> unauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String body = String.format("{\"error\":\"%s\"}", message);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    /**
     * 필터 우선순위 설정
     * 낮은 숫자일수록 먼저 실행됨
     */
    @Override
    public int getOrder() {
        return -100; // 다른 필터보다 먼저 실행
    }
}
