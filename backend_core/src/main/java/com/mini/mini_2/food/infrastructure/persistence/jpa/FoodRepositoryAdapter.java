package com.mini.mini_2.food.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mini.mini_2.food.domain.FoodRepository;
import com.mini.mini_2.food.domain.entity.FoodEntity;

import lombok.RequiredArgsConstructor;

/**
 * 도메인 리포지토리 인터페이스(FoodRepository)의
 * Spring Data JPA 구현체(어댑터)
 */
@Repository
@RequiredArgsConstructor
public class FoodRepositoryAdapter implements FoodRepository {

    private final SpringDataFoodRepository jpa;

    @Override
    public FoodEntity save(FoodEntity e) {
        return jpa.save(e);
    }

    @Override
    public Optional<FoodEntity> findById(Integer id) {
        return jpa.findById(id);
    }

    @Override
    public List<FoodEntity> findAll() {
        return jpa.findAll();
    }

    @Override
    public void delete(FoodEntity e) {
        jpa.delete(e);
    }
}