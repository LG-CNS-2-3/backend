package com.lgcns.backend_map.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lgcns.backend_map.dto.request.CoordinateReqDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoordinateListDeserializer extends JsonDeserializer<List<CoordinateReqDTO>> {
    @Override
    public List<CoordinateReqDTO> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String valueString = p.getValueAsString();
        List<CoordinateReqDTO> coordinateReqDtos = new ArrayList<>();

        if(valueString == null || valueString.isEmpty()) return coordinateReqDtos;

        for(String coordinateString : valueString.split("_")){
            String[] coordinateArr = coordinateString.split(",");
            coordinateReqDtos.add(new CoordinateReqDTO(Double.parseDouble(coordinateArr[0]), Double.parseDouble(coordinateArr[1])));
        }
        return coordinateReqDtos;
    }
}
