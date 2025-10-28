package com.mini.mini_2.facility.application.dto;

import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FacilityRequestDTO {
    @Schema(example = "1", description = "휴게소 Id") private Integer restAreaId;
    @Schema(example = "화장실", description = "편의 시설 이름") private String name;
    @Schema(example = "남/녀, 장애일 화장실이 있습니다.", description = "편의 시설 설명") private String  description;
    
    public FacilityEntity toEntity(RestAreaEntity restArea) {
        return FacilityEntity.builder()
                .restArea(restArea)
                .name(this.name)
                .description(this.description)
                .build();
    }
    
}
