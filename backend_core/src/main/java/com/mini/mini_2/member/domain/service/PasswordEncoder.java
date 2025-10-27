package com.mini.mini_2.member.domain.service;

import com.mini.mini_2.member.domain.vo.Password;

/**
 * 비밀번호 암호화 인터페이스 (Domain이 정의)
 * 구현은 Infrastructure가 담당
 */
public interface PasswordEncoder {

    Password encode(Password rawPassword);
    boolean matches(Password rawPassword, Password encodedPassword);
}
