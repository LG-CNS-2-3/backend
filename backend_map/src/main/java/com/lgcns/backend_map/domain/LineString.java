package com.lgcns.backend_map.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LineString extends Feature{
    List<Coordinate> coordinates;

    @Override
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    @Override
    public String getType() {
        return "LineString";
    }
}
