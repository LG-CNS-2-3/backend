package com.lgcns.backend_map.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgcns.backend_map.application.spi.PlaceSearchApi;
import com.lgcns.backend_map.core.exception.ExternalServiceUnavailableException;
import com.lgcns.backend_map.domain.Place;
import com.lgcns.backend_map.infra.dto.PlaceSearchApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TMapPlaceSearchApi implements PlaceSearchApi {
    private static final String POI_SEARCH_PATH = "/tmap/pois";

    private final TMapApiProperties tMapApiProperties;
    private final ObjectMapper objectMapper;

    @Override
    public List<Place> searchPoi(String query) {
        RestClient restClient = RestClient.builder().baseUrl(tMapApiProperties.getUrl()).build();

        String jsonString;
        try{
            jsonString = restClient.get()
                    .uri(uriBuilder -> uriBuilder.path(POI_SEARCH_PATH)
                            .queryParam("version", 1)
                            .queryParam("searchKeyword", query)
                            .queryParam("appKey", tMapApiProperties.getKey())
                            .queryParam("count", 5)
                            .build()
                    )
                    .retrieve()
                    .body(String.class);
        }catch (Exception e){
            log.error("TMap 장소 검색 API 오류 발생");
            throw new ExternalServiceUnavailableException();
        }

        PlaceSearchApiResponse apiResponse = parseJsonString(jsonString);

        return apiResponse.toDomain();
    }

    private PlaceSearchApiResponse parseJsonString(String jsonString){
        try{
            if(jsonString == null || jsonString.isEmpty()) return new PlaceSearchApiResponse(List.of());

            JsonNode root =  objectMapper.readTree(jsonString);
            JsonNode poiArrayNode = root.get("searchPoiInfo").get("pois").get("poi");

            List<PlaceSearchApiResponse.PlaceInfo> places =  new ArrayList<>();

            for(JsonNode poiNode: poiArrayNode){
                String name = poiNode.get("name").asText();
                Double latitude = poiNode.get("noorLat").asDouble();
                Double longitude = poiNode.get("noorLon").asDouble();

                JsonNode newAddressNode = poiNode.get("newAddressList").get("newAddress").get(0);
                String roadNameAddress = newAddressNode.get("fullAddressRoad").asText();

                places.add(new PlaceSearchApiResponse.PlaceInfo(name, latitude, longitude, roadNameAddress));
            }

            return new PlaceSearchApiResponse(places);
        }catch(JsonProcessingException e){
            log.error("TMap 장소 검색 API 오류 발생");
            throw new ExternalServiceUnavailableException();
        }
    }
}
