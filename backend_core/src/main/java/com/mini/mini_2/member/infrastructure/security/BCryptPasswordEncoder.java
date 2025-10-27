package com.mini.mini_2.member.infrastructure.security;

import com.mini.mini_2.member.domain.service.PasswordEncoder;
import com.mini.mini_2.member.domain.vo.Password;
import org.springframework.stereotype.Component;

/**
 * BCrypt 암호화 구현체 (Infrastructure)
 */
@Component
public class BCryptPasswordEncoder implements PasswordEncoder {

    private final org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder;

    public BCryptPasswordEncoder() {
        this.encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    @Override
    public Password encode(Password rawPassword) {
        String encoded = encoder.encode(rawPassword.getValue());
        return new Password(encoded);
    }

    @Override
    public boolean matches(Password rawPassword, Password encodedPassword) {
        return encoder.matches(rawPassword.getValue(), encodedPassword.getValue());
    }
}
