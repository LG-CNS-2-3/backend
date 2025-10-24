package com.lgcns.backend_map.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CoordinateResDTO(
        @Schema(example = "", description = "경도 좌표") Double xCoord,
        @Schema(example = "", description = "위도 좌표") Double yCoord
) {
    public static CoordinateResDTO of(Double xCoord, Double yCoord){
        return new CoordinateResDTO(xCoord, yCoord);
    }
}
