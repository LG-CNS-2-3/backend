package com.lgcns.backend_map.infra.dto;

public record RestAreasSearchPayload(
        String startX,
        String startY,
        String endX,
        String endY,
        String userX,
        String userY,
        String radius,
        String searchType,
        String searchCategory,
        String lineString
) {
}
