package com.mini.mini_2.openapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mini.mini_2.openapi.domain.dto.RestAreaInfoApiRequestDTO;
import com.mini.mini_2.openapi.domain.dto.RestAreaInfoApiResponseDTO;

@Service
public class RestAreaInfoApiService {
    private final WebClient restAreaInfoWebClient;
    private final String openApiRestAreaInfoUrl;
    private final String appKey;

    public RestAreaInfoApiService(WebClient.Builder builder,
            @Value("${openapi.key}") String appKey,
            @Value("${openapi.info-url}") String openApiRestAreaUrl) {
        this.appKey = appKey;
        this.openApiRestAreaInfoUrl = openApiRestAreaUrl;
        this.restAreaInfoWebClient = builder
                .build();
    }

    public RestAreaInfoApiResponseDTO info(RestAreaInfoApiRequestDTO request) {
        String url = openApiRestAreaInfoUrl +
                "?type=json" +
                "&svarGsstClssCd=0" +
                "&key=" + appKey;
        if (request.getSvarCd() != "") {
            url = url + "&stdRestCd=" + request.getSvarCd();
        }
        System.out.println("[WEB CLIENT] GET URL " + url);
        return restAreaInfoWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(RestAreaInfoApiResponseDTO.class)
                .block();
    }
}
