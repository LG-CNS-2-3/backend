package com.mini.mini_2.member.domain.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("회원을 찾을 수 없습니다");
    }

    public MemberNotFoundException(Long id) {
        super("회원을 찾을 수 없습니다 (ID: " + id + ")");
    }
}
