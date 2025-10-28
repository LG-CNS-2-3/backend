package com.mini.mini_2.rest_area.application.dto;

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
public class RestAreaRequestDTO {
    
    @Schema(example = "고양휴게소", description = "휴게소 이름") private String name;
    @Schema(example = "up", description = "방향 영어로 입력 : (상행 : up / 하행 : down)") private String direction;
    @Schema(example = "0001", description = "휴게소 이름") private String code;
    @Schema(example = "031-123-4567", description = "휴게소 전화번호") private String tel ;
    @Schema(example = "경기도 고양시 덕양구", description = "휴게소 주소") private String address ;
    @Schema(example = "문산고속도로", description = "고속도로 이름") private String routeName ;
    private String xValue ;
    private String yValue ;
    
    public RestAreaEntity toEntity() {
        return RestAreaEntity.builder()
                             .name(this.name)
                             .direction(this.direction.toLowerCase())
                             .code(this.code)
                             .tel(this.tel)
                             .address(this.address)
                             .routeName(this.routeName)
                             .xValue(this.xValue)
                             .yValue(this.yValue)
                             .build();
    }
}

