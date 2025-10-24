package com.lgcns.backend_map.application;

import com.lgcns.backend_map.application.spi.PlaceSearchApi;
import com.lgcns.backend_map.application.spi.RouteSearchApi;
import com.lgcns.backend_map.domain.Feature;
import com.lgcns.backend_map.domain.Place;

import com.lgcns.backend_map.dto.request.RouteSearchReqDTO;
import com.lgcns.backend_map.dto.response.FeatureResDTO;
import com.lgcns.backend_map.dto.response.PlaceResDTO;
import com.lgcns.backend_map.dto.response.PlaceSearchResDTO;
import com.lgcns.backend_map.dto.response.RouteSearchResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final PlaceSearchApi placeSearchApi;
    private final RouteSearchApi routeSearchApi;

    public PlaceSearchResDTO searchPlace(String query){
        List<Place> places = placeSearchApi.searchPoi(query);

        return PlaceSearchResDTO.of(
                places.stream().map(PlaceResDTO::from).toList()
        );
    }

    public RouteSearchResDTO searchRoute(RouteSearchReqDTO reqDTO){
        List<Feature> features = routeSearchApi.searchRoute(reqDTO.startX(), reqDTO.startY(), reqDTO.endX(), reqDTO.endY());

        return RouteSearchResDTO.of(
                features.stream()
                        .map(
                             FeatureResDTO::from)
                        .toList());
    }
}
