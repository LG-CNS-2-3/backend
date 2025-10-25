package com.lgcns.backend_map.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.backend_map.application.spi.RouteSearchApi;
import com.lgcns.backend_map.core.exception.ExternalServiceUnavailableException;
import com.lgcns.backend_map.domain.Coordinate;
import com.lgcns.backend_map.domain.Feature;
import com.lgcns.backend_map.domain.LineString;
import com.lgcns.backend_map.domain.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TMapRouteSearchApi implements RouteSearchApi {
    private static final String TMAP_ROUTE_PATH = "/tmap/routes";

    private final TMapApiProperties tMapApiProperties;
    private final ObjectMapper objectMapper;

    @Override
    public List<Feature> searchRoute(Double startX, Double startY, Double endX, Double endY) {
        RestClient client = RestClient.builder().baseUrl(tMapApiProperties.getUrl()).build();

        String jsonString = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(TMAP_ROUTE_PATH)
                        .queryParam("version", 1)
                        .queryParam("appKey", tMapApiProperties.getKey())
                        .queryParam("startX", startX)
                        .queryParam("startY", startY)
                        .queryParam("endX", endX)
                        .queryParam("endY", endY)
                        .build())
                .retrieve()
                .body(String.class);

        return parseJsonString(jsonString);
    }

    private List<Feature> parseJsonString(String jsonString){
        try{
            JsonNode featureArrayNode = objectMapper.readTree(jsonString).get("features");
            List<Feature> features = new ArrayList<>();

            for(JsonNode featureNode : featureArrayNode){
                JsonNode geometryNode = featureNode.get("geometry");

                String type = geometryNode.get("type").asText();

                JsonNode coordinateArrayNode = geometryNode.get("coordinates");

                if(type.equals("Point")){
                    Double xCoord = coordinateArrayNode.get(0).asDouble();
                    Double yCoord = coordinateArrayNode.get(1).asDouble();

                    Coordinate coord = new Coordinate(xCoord, yCoord);
                    features.add(new Point(coord));
                }else{
                    Double startXCoord = coordinateArrayNode.get(0).get(0).asDouble();
                    Double startYCoord = coordinateArrayNode.get(0).get(1).asDouble();
                    Coordinate startCoord = new Coordinate(startXCoord, startYCoord);

                    Double endXCoord = coordinateArrayNode.get(1).get(0).asDouble();
                    Double endYCoord = coordinateArrayNode.get(1).get(1).asDouble();
                    Coordinate endCoord = new Coordinate(endXCoord, endYCoord);

                    features.add(new LineString(startCoord, endCoord));
                }
            }
            return features;
        }catch(JsonProcessingException e){
            log.error("TMap 경로 검색 API 오류 발생");
            throw new ExternalServiceUnavailableException();
        }
    }
}
