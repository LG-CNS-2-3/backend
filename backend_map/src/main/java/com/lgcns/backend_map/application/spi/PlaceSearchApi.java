package com.lgcns.backend_map.application.spi;

import com.lgcns.backend_map.domain.Place;

import java.util.List;

public interface PlaceSearchApi {
    List<Place> searchPoi(String searchKeyword);
}
