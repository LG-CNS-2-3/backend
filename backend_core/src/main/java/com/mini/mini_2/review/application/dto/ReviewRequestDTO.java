package com.mini.mini_2.review.application.dto;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.review.domain.entity.ReviewEntity;
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
    private Integer userId;
    private Integer restAreaId;
    
    private String rating;
    private String comment;
    
    public ReviewEntity toEntity(Member member, RestAreaEntity restAreaEntity) {
        return ReviewEntity.builder()
                         .member(member)
                         .restArea(restAreaEntity)
                         .rating(this.rating)
                         .comment(this.comment)
                         .build();
    }
}
