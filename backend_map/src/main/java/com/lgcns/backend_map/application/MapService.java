package com.lgcns.backend_map.application;

import com.lgcns.backend_map.application.spi.PlaceSearchApi;
import com.lgcns.backend_map.domain.Place;

import com.lgcns.backend_map.dto.response.PlaceResDTO;
import com.lgcns.backend_map.dto.response.PlaceSearchResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final PlaceSearchApi placeSearchApi;

    public PlaceSearchResDTO searchPlace(String query){
        List<Place> places = placeSearchApi.searchPoi(query);

        return PlaceSearchResDTO.of(
                places.stream().map(PlaceResDTO::from).toList()
        );
    }
}
