package com.lgcns.backend_map.dto.response;

import com.lgcns.backend_map.domain.Place;

public record PlaceResDTO(
        String name,
        String roadNameAddress,
        Double latitude,
        Double longitude
) {

    public static PlaceResDTO from(Place place){
        return new PlaceResDTO(place.getName(), place.getRoadNameAddress(), place.getLatitude(), place.getLongitude());
    }
}
