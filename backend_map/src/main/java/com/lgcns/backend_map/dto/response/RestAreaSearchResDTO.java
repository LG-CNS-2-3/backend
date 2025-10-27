package com.lgcns.backend_map.dto.response;

import java.util.List;

public record RestAreaSearchResDTO(
        List<PlaceResDTO> places
) {
    public static RestAreaSearchResDTO of(List<PlaceResDTO> places) {
        return new  RestAreaSearchResDTO(places);
    }
}
