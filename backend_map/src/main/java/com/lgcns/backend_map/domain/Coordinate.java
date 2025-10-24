package com.lgcns.backend_map.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Coordinate {
    private Double xCoord;
    private Double yCoord;
}
