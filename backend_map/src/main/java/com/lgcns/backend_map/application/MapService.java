package com.lgcns.backend_map.application;

import com.lgcns.backend_map.application.spi.PlaceSearchApi;
import com.lgcns.backend_map.application.spi.RouteRestAreaSearchApi;
import com.lgcns.backend_map.application.spi.RouteSearchApi;
import com.lgcns.backend_map.domain.Coordinate;
import com.lgcns.backend_map.domain.Feature;
import com.lgcns.backend_map.domain.Place;

import com.lgcns.backend_map.dto.request.RestAreaSearchReqDTO;
import com.lgcns.backend_map.dto.request.RouteSearchReqDTO;
import com.lgcns.backend_map.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.webmvc.core.service.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final PlaceSearchApi placeSearchApi;
    private final RouteSearchApi routeSearchApi;
    private final RouteRestAreaSearchApi routeRestAreaSearchApi;

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

    public RestAreaSearchResDTO searchRestArea(RestAreaSearchReqDTO reqDTO){
        List<Place> places = routeRestAreaSearchApi.searchRestAreaOnRoute(
                reqDTO.startX(),
                reqDTO.startY(),
                reqDTO.endX(),
                reqDTO.endY(),
                reqDTO.userX(),
                reqDTO.userY(),
                reqDTO.radius(),
                reqDTO.lineString()
        );

        return RestAreaSearchResDTO.of(
                places.stream().map(PlaceResDTO::from).toList());
    }
}
