package com.lgcns.backend_map.infra;

import com.lgcns.backend_map.application.spi.RouteRestAreaSearchApi;
import com.lgcns.backend_map.domain.LineString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TmapRouteRestAreaSearchApi implements RouteRestAreaSearchApi {
    private static final String POI_ROUTE_SEARCH_PATH = "/tmap/poi/findPoiRoute";
    private static final String SEARCH_TYPE_VALUE = "CATEGORY";
    private static final String SEARCH_CATEGORY_VALUE = "C01";

    private final TMapApiProperties tMapApiProperties;

    @Override
    public void searchRestAreaOnRoute(
            Double startX,
            Double startY,
            Double endX,
            Double endY,
            Double userX,
            Double userY,
            List<LineString> lineStrings
    ) {
        RestClient restClient = RestClient.builder().baseUrl(tMapApiProperties.getUrl()).build();
//
//        restClient.post()
//                .uri(URI.create(POI_ROUTE_SEARCH_PATH))
//                .body()
//                .retrieve()
    }

    private Map<String, Object> buildPayLoad(
            Double startX,
            Double startY,
            Double endX,
            Double endY,
            Double userX,
            Double userY,
            List<LineString> lineStrings
    ){
        Map<String, Object> body = new HashMap<>();

        body.put("startX", startX);
        body.put("startY", startY);
        body.put("endX", endX);
        body.put("endY", endY);
        body.put("userX", userX);
        body.put("userY", userY);
        body.put("searchType", SEARCH_TYPE_VALUE);
        body.put("searchCategory", SEARCH_CATEGORY_VALUE);
        body.put("lineStrings", 0);

        return body;
    }
}
