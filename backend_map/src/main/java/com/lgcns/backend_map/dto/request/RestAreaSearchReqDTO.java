package com.lgcns.backend_map.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RestAreaSearchReqDTO(
        @Schema(name = "출발지 경도 좌표", example = "127.10845476") Double startX,
        @Schema(name = "출발지 위도 좌표", example = "37.55470543") Double startY,
        @Schema(name = "도착지 경도 좌표", example = "126.95310969") Double endX,
        @Schema(name = "도착지 위도 좌표", example = "37.53104075") Double endY,
        @Schema(name = "사용자 경도 좌표", example = "127.10845476") Double userX,
        @Schema(name = "사용자 위도 좌표", example = "37.55470543") Double userY,
        @Schema(name = "탐색 반경", example = "5") Integer radius,
        @Schema(name = "경로 상의 모든 lineString 값", example = "") String lineString
) {
}
