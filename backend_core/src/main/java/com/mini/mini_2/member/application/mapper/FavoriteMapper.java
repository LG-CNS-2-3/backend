package com.mini.mini_2.member.application.mapper;

import com.mini.mini_2.member.application.dto.response.FavoriteResponse;
import com.mini.mini_2.member.domain.Favorite;
import org.springframework.stereotype.Component;

/**
 * Favorite <-> DTO 변환 Mapper
 */
@Component
public class FavoriteMapper {

    public FavoriteResponse toResponse(Favorite favorite) {
        return new FavoriteResponse(
            favorite.getId(),
            favorite.getMember().getId(),
            favorite.getRestArea().getRestAreaId(),
            favorite.getDescription()
        );
    }
}
