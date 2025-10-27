package com.mini.mini_2.member.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 기존 TokenService를 개선하여 Infrastructure로 이동
 */
@Component
public class JwtTokenProvider {

    private final RedisTokenStore tokenStore;
    private final String jwtSecret;
    private final long accessTokenValidityMs;
    private final long refreshTokenValidityMs;

    public JwtTokenProvider(
        RedisTokenStore tokenStore,
        @Value("${jwt.secret}") String jwtSecret,
        @Value("${jwt.access-ttl-ms}") long accessTokenValidityMs,
        @Value("${jwt.refresh-ttl-ms}") long refreshTokenValidityMs
    ) {
        this.tokenStore = tokenStore;
        this.jwtSecret = jwtSecret;
        this.accessTokenValidityMs = accessTokenValidityMs;
        this.refreshTokenValidityMs = refreshTokenValidityMs;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long memberId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidityMs);

        String token = Jwts.builder()
                .setSubject(memberId.toString())
                .claim("type", "access")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        tokenStore.storeAccessToken(token, memberId.toString(), accessTokenValidityMs);
        return token;
    }

    public String generateRefreshToken(Long memberId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidityMs);

        String token = Jwts.builder()
                .setSubject(memberId.toString())
                .claim("type", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        tokenStore.storeRefreshToken(token, memberId.toString(), refreshTokenValidityMs);
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return tokenStore.existsAccessToken(token);
        } catch (Exception e) {
            return false;
        }
    }

    public void invalidateAccessToken(String token) {
        tokenStore.deleteAccessToken(token);
    }

    public void invalidateRefreshToken(String token) {
        tokenStore.deleteRefreshToken(token);
    }

    public TokenPair refreshWithRotation(String refreshToken) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (Exception e) {
            return null;
        }

        String memberId = claims.getSubject();

        if (!tokenStore.existsRefreshToken(refreshToken)) {
            return null;
        }

        invalidateRefreshToken(refreshToken);
        String newRefresh = generateRefreshToken(Long.parseLong(memberId));
        String newAccess = generateAccessToken(Long.parseLong(memberId));

        return new TokenPair(newAccess, newRefresh);
    }

    public Long getMemberIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public record TokenPair(String accessToken, String refreshToken) {}
}
