package com.mini.mini_2.member.application.dto.response;

public record FavoriteResponse(
    Long id,
    Long memberId,
    Integer restAreaId,
    String description
) {}
