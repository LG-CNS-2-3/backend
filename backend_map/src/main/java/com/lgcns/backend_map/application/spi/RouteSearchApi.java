package com.lgcns.backend_map.application.spi;

import com.lgcns.backend_map.domain.Feature;

import java.util.List;

public interface RouteSearchApi {
    List<Feature> searchRoute(Double startX, Double startY, Double endX, Double endY);
}
