package com.lgcns.backend_map.application.spi;

import com.lgcns.backend_map.domain.Coordinate;
import com.lgcns.backend_map.domain.LineString;
import com.lgcns.backend_map.domain.Place;

import java.util.List;

public interface RouteRestAreaSearchApi {
    List<Place> searchRestAreaOnRoute(
            Double startX,
            Double startY,
            Double endX,
            Double endY,
            Double userX,
            Double userY,
            Integer radius,
            String lineString
    );
}
