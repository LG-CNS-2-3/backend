package com.lgcns.backend_map.dto.response;

import java.util.List;

public record RouteSearchResDTO(
        List<FeatureResDTO> features
) {
    public static RouteSearchResDTO of(List<FeatureResDTO> features){
         return new RouteSearchResDTO(features);
    }
}
