package com.mini.mini_2.member.application.dto.command;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterMemberCommand(
    @Schema(example = "abcde@naver.com", description = "이메일 형식에 맞게 입력") String email,
    @Schema(example = "abcde12345", description = "비밀번호는 숫자를 포함하여 8글자 이상") String password,
    @Schema(example = "홍길동", description = "2자 이상 20자 이하") String nickname
) {}
