package com.mini.mini_2.member.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 토큰 저장소 (Infrastructure)
 * Redis 로직을 분리
 */
@Component
@RequiredArgsConstructor
public class RedisTokenStore {

    private final RedisTemplate<String, Object> redisTemplate;

    private String accessKey(String token) {
        return "access:" + token;
    }

    private String refreshKey(String token) {
        return "refresh:" + token;
    }

    public void storeAccessToken(String token, String memberId, long ttlMs) {
        redisTemplate.opsForValue().set(accessKey(token), memberId, ttlMs, TimeUnit.MILLISECONDS);
    }

    public void storeRefreshToken(String token, String memberId, long ttlMs) {
        redisTemplate.opsForValue().set(refreshKey(token), memberId, ttlMs, TimeUnit.MILLISECONDS);
    }

    public boolean existsAccessToken(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(accessKey(token)));
    }

    public boolean existsRefreshToken(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(refreshKey(token)));
    }

    public void deleteAccessToken(String token) {
        redisTemplate.delete(accessKey(token));
    }

    public void deleteRefreshToken(String token) {
        redisTemplate.delete(refreshKey(token));
    }
}
