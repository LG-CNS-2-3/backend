package com.lgcns.backend_map.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public record PlaceSearchResDTO(
        List<PlaceResDTO> places
) {
    public static PlaceSearchResDTO of(List<PlaceResDTO> places){
        return new PlaceSearchResDTO(places);
    }
}
