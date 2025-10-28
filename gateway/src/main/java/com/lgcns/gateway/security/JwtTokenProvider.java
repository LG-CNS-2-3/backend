package com.lgcns.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenProvider {

    private final RedisTokenStore tokenStore;
    private final String jwtSecret;

    public JwtTokenProvider(
        RedisTokenStore tokenStore,
        @Value("${jwt.secret}") String jwtSecret
    ) {
        this.tokenStore = tokenStore;
        this.jwtSecret = jwtSecret;
    }

    // JWT 서명 검증에 사용할 비밀키 생성
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 토큰 검증
    public Mono<Boolean> validateToken(String token) {
        try {
            // JWT 파싱 및 서명 검증
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

            // Redis에서 토큰 존재 여부 확인 (비동기)
            return tokenStore.existsAccessToken(token);
        } catch (Exception e) {
            return Mono.just(false);
        }
    }

    // 토큰에서 memberId 추출
    public Long getMemberIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        return Long.parseLong(claims.getSubject());
    }
}
