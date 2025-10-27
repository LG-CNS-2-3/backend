package com.mini.mini_2.member.application.dto.response;

public record LoginResponse(
    MemberResponse member,
    String accessToken,
    String refreshToken
) {}
