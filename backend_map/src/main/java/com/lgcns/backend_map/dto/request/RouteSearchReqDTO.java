package com.lgcns.backend_map.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RouteSearchReqDTO(
        @Schema(example = "127.10845476", description = "출발지 경도") Double startX,
        @Schema(example = "37.52673893", description = "출발지 위도") Double startY,
        @Schema(example = "129.04140556", description = "도착지 경도") Double endX,
        @Schema(example = "35.11510481", description = "도착지 위도") Double endY
) {
}
