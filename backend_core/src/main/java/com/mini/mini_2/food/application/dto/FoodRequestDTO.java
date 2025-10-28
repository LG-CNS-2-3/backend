package com.mini.mini_2.food.application.dto; 

import com.mini.mini_2.food.domain.entity.FoodEntity; 
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
public class FoodRequestDTO {
    @Schema(example = "1", description = "휴게소 id") private Integer restAreaId;
    @Schema(example = "짬뽕", description = "음식 이름") private String  foodName;
    @Schema(example = "가격", description = "11000") private String  price;
    @Schema(example = "Y", description = "시그니처 여부(Y 또는 N)") private String  isSignature;
    @Schema(example = "맵고 맛있는 짬뽕", description = "음식에 대한 설명") private String  description;

    public FoodEntity toEntity(RestAreaEntity restArea) {
        return FoodEntity.builder()
                .restArea(restArea) 
                .foodName(this.foodName)
                .price(this.price)
                .isSignature(this.isSignature)
                .description(this.description)
                .build();
    }
}