package com.lgcns.backend_map.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.backend_map.application.spi.RouteRestAreaSearchApi;
import com.lgcns.backend_map.core.exception.ExternalServiceUnavailableException;
import com.lgcns.backend_map.domain.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TmapRouteRestAreaSearchApi implements RouteRestAreaSearchApi {
    private static final String POI_ROUTE_SEARCH_PATH = "/tmap/poi/findPoiRoute";
    private static final String SEARCH_TYPE_VALUE = "category";
    private static final String SEARCH_CATEGORY_VALUE = "C01";

    private final ObjectMapper objectMapper;
    private final TMapApiProperties tMapApiProperties;

    @Override
    public List<Place> searchRestAreaOnRoute(
            Double startX,
            Double startY,
            Double endX,
            Double endY,
            Double userX,
            Double userY,
            Integer radius,
            String lineString
    ) {
        RestClient restClient = RestClient.builder()
                .baseUrl(tMapApiProperties.getUrl())
                .defaultHeader("appKey", tMapApiProperties.getKey())
                .build();

        String jsonString;
        try{
            jsonString = restClient.post()
                    .uri(uriBuilder -> uriBuilder.path(POI_ROUTE_SEARCH_PATH)
                            .queryParam("version", "1.0")
                            .build())
                    .contentType(MediaType.valueOf("application/json; charset=UTF-8"))
                    .body(buildPayLoad(startX, startY, endX, endY, userX, userY, radius, lineString))
                    .retrieve()
                    .body(String.class);
        }catch(Exception e){
            log.error("TMAP 경로 반경 검색 API 오류 발생");
            throw new ExternalServiceUnavailableException();
        }

        return parseJsonString(jsonString);
    }

    private Map<String, String> buildPayLoad(
            Double startX,
            Double startY,
            Double endX,
            Double endY,
            Double userX,
            Double userY,
            Integer radius,
            String lineString
    ){
        Map<String, String> body = new HashMap<>();

        body.put("startX", String.valueOf(startX));
        body.put("startY", String.valueOf(startY));
        body.put("endX", String.valueOf(endX));
        body.put("endY", String.valueOf(endY));
        body.put("userX", String.valueOf(userX));
        body.put("userY", String.valueOf(userY));
        body.put("radius", String.valueOf(radius));
        body.put("searchType", SEARCH_TYPE_VALUE);
        body.put("searchCategory", SEARCH_CATEGORY_VALUE);
        body.put("lineString", lineString);

        return body;
    }

    private List<Place> parseJsonString(String jsonString){
        try{
            if(jsonString == null || jsonString.isEmpty()) return List.of();

            JsonNode rootNode = objectMapper.readTree(jsonString);
            JsonNode poiNodes = rootNode.get("searchPoiInfo").get("pois").get("poi");

            List<Place> places = new ArrayList<>();
            for(JsonNode poiNode : poiNodes){
                Double latitude = poiNode.get("centerLat").asDouble();
                Double longitude = poiNode.get("centerLon").asDouble();
                String name = poiNode.get("name").asText();
                String roadNameAddress = poiNode.get("roadName").asText();

                places.add(new Place(name, roadNameAddress, latitude, longitude));
            }

            return places;
        }catch(JsonProcessingException e){
            log.error("TMAP 경로 반경 검색 API 오류 발생");
            throw new ExternalServiceUnavailableException();
        }
    }
}
