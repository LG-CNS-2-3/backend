package com.mini.mini_2.member.application.dto.command;

public record AddFavoriteCommand(
    Long memberId,
    Integer restAreaId,
    String description
) {}
