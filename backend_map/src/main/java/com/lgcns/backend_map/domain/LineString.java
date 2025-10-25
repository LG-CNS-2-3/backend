package com.lgcns.backend_map.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LineString extends Feature{
    Coordinate start;
    Coordinate end;

    @Override
    public List<Coordinate> getCoordinates() {
        return List.of(start, end);
    }

    @Override
    public String getType() {
        return "LineString";
    }
}
