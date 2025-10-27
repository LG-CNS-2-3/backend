package com.lgcns.backend_map.infra.dto;

import com.lgcns.backend_map.domain.Place;

import java.util.List;

public record PlaceSearchApiResponse(
        List<PlaceInfo> places
) {
    public record PlaceInfo(String name, Double latitude, Double longitude, String roadAddressName){
    }

    public List<Place> toDomain(){
        return places
                .stream().map(p ->
                        Place.builder()
                                .name(p.name)
                                .latitude(p.latitude)
                                .longitude(p.longitude)
                                .roadNameAddress(p.roadAddressName)
                                .build()
                ).toList();
    }
}
