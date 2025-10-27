package com.mini.mini_2.favorite.domain;

import java.util.List;

import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;

/**
 * Favorite 도메인 리포지토리 인터페이스 (Application 계층이 의존하는 포트)
 */
public interface FavoriteRepository {

    FavoriteEntity save(FavoriteEntity e);

    void deleteById(Integer favoriteId);

    List<FavoriteEntity> findAllByUser_UserId(Integer userId);
}