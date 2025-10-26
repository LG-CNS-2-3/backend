package com.lgcns.backend_map.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Place {
    private String name;
    private String roadNameAddress;
    private Double latitude;
    private Double longitude;
}
