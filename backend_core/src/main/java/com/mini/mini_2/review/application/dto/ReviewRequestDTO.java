package com.mini.mini_2.review.application.dto;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.review.domain.entity.ReviewEntity;

import io.swagger.v3.oas.annotations.media.Schema;

import com.mini.mini_2.member.domain.Member;

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
public class ReviewRequestDTO {
    @Schema(example = "1", description = "사용자 Id") private Integer memberId;
    @Schema(example = "2", description = "휴게소 Id") private Integer restAreaId;
    
    @Schema(example = "4", description = "1 ~ 5 사이의 평점 입력") private String rating;
    @Schema(example = "아이들과 함께 오기 좋아요", description = "리뷰 작성") private String comment;
    
    public ReviewEntity toEntity(Member member, RestAreaEntity restAreaEntity) {
        return ReviewEntity.builder()
                         .member(member)
                         .restArea(restAreaEntity)
                         .rating(this.rating)
                         .comment(this.comment)
                         .build();
    }
}
