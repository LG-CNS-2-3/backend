package com.mini.mini_2.food.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mini.mini_2.food.domain.FoodRepository;
import com.mini.mini_2.food.domain.entity.FoodEntity;

import lombok.RequiredArgsConstructor;

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

    // [수정] 추가된 3개 메서드 구현
    @Override
    public List<FoodEntity> findByFoodNameContaining(String keyword) {
        return jpa.findByFoodNameContaining(keyword);
    }

    @Override
    public List<FoodEntity> findByRestAreaIdAndIsSignature(Integer restAreaId, String isSignature) {
        return jpa.findByRestArea_RestAreaIdAndIsSignature(restAreaId, isSignature);
    }

    @Override
    public List<FoodEntity> findByPriceLessThanEqual(double maxPrice) {
        return jpa.findByPriceLessThanEqual(maxPrice);
    }
}