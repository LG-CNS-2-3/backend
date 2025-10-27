package com.mini.mini_2.member.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 비밀번호 값 객체
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    private static final int MIN_LENGTH = 8;

    private String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다");
        }
        // BCrypt 암호화된 비밀번호는 검증 스킵
        if (value.length() < MIN_LENGTH && !isEncoded(value)) {
            throw new IllegalArgumentException("비밀번호는 최소 " + MIN_LENGTH + "자 이상이어야 합니다");
        }
    }

    private boolean isEncoded(String value) {
        return value.startsWith("$2a$") || value.startsWith("$2b$");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
