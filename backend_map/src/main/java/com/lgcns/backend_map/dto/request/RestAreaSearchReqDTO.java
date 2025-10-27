package com.lgcns.backend_map.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RestAreaSearchReqDTO(
        @Schema(description = "출발지 경도 좌표", example = "127.10845476") Double startX,
        @Schema(description = "출발지 위도 좌표", example = "37.55470543") Double startY,
        @Schema(description = "도착지 경도 좌표", example = "126.95310969") Double endX,
        @Schema(description = "도착지 위도 좌표", example = "37.53104075") Double endY,
        @Schema(description = "사용자 경도 좌표", example = "127.10845476") Double userX,
        @Schema(description = "사용자 위도 좌표", example = "37.55470543") Double userY,
        @Schema(description = "탐색 반경", example = "5") Integer radius,
        @Schema(description = "경로 상의 모든 lineString 값", example = "") String lineString
) {
}
