package com.lgcns.backend_map.domain;

import java.util.List;

public abstract class Feature {
    public abstract String getType();
    public abstract List<Coordinate> getCoordinates();
}
