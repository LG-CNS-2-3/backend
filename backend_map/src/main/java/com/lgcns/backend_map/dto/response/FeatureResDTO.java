package com.lgcns.backend_map.dto.response;

import com.lgcns.backend_map.domain.Feature;
import com.lgcns.backend_map.domain.Point;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public record FeatureResDTO(
        @Schema(example = "Point", description = "Feature 타입(Point, LineString)") String type,
        @Schema(description = "관련 좌표들(Point 는 1개 LineString 는 2개 이상") List<CoordinateResDTO> coordinates
) {
    public static FeatureResDTO from(Feature feature){
        return new FeatureResDTO(
                feature.getType(),
                feature.getCoordinates().stream()
                        .map(c -> CoordinateResDTO.of(c.getXCoord(), c.getYCoord()))
                        .toList()
        );
    }
}
