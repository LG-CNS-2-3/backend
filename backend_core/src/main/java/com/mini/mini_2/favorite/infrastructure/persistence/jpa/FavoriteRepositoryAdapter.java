package com.mini.mini_2.favorite.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mini.mini_2.favorite.domain.FavoriteRepository;
import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;

import lombok.RequiredArgsConstructor;

/**
 * 도메인 리포지토리(FavoriteRepository)의 Spring Data JPA 구현체(어댑터)
 */
@Repository
@RequiredArgsConstructor
public class FavoriteRepositoryAdapter implements FavoriteRepository {

    private final SpringDataFavoriteRepository jpa;

    @Override
    public FavoriteEntity save(FavoriteEntity e) {
        return jpa.save(e);
    }

    @Override
    public void deleteById(Integer favoriteId) {
        jpa.deleteById(favoriteId);
    }

    @Override
    public List<FavoriteEntity> findAllByUser_UserId(Integer userId) {
        return jpa.findAllByUser_UserId(userId);
    }
}