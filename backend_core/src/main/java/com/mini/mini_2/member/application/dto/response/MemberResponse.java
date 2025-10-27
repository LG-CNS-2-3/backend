package com.mini.mini_2.member.application.dto.response;

import java.time.LocalDateTime;

public record MemberResponse(
    Long id,
    String email,
    String nickname,
    LocalDateTime createdAt
) {}
