package com.mini.mini_2.member.application.dto.command;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddFavoriteCommand(
    @Schema(example = "1", description = "사용자 Id") Long memberId,
    @Schema(example = "1", description = "휴게소 Id") Integer restAreaId,
    @Schema(example = "아이들과 가기 좋습니다", description = "휴게소 설명") String description
) {}
