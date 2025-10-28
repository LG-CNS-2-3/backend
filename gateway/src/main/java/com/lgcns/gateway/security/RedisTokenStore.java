package com.lgcns.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Redis 토큰 저장소 (Reactive 버전)
 * Gateway는 WebFlux 기반이므로 비동기 방식으로 Redis 조회
 */
@Component
@RequiredArgsConstructor
public class RedisTokenStore {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    private String accessKey(String token) {
        return "access:" + token;
    }

    /**
     * Access Token 존재 여부 확인 (비동기)
     * @param token JWT 토큰
     * @return Mono<Boolean> - 토큰이 Redis에 존재하면 true
     */
    public Mono<Boolean> existsAccessToken(String token) {
        return reactiveRedisTemplate.hasKey(accessKey(token));
    }
}
