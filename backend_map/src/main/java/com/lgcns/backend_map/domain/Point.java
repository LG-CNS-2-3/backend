package com.lgcns.backend_map.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Point extends Feature{
    Coordinate coord;

    @Override
    public List<Coordinate> getCoordinates() {
        return List.of(coord);
    }

    @Override
    public String getType() {
        return "Point";
    }
}
