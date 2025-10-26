package com.mini.mini_2.food.domain;

import java.util.List;
import java.util.Optional;
import com.mini.mini_2.food.domain.entity.FoodEntity;

public interface FoodRepository {

    FoodEntity save(FoodEntity e);
    Optional<FoodEntity> findById(Integer id);
    List<FoodEntity> findAll();
    void delete(FoodEntity e);

    // [수정] 서비스 계층에서 사용할 메서드 정의
    List<FoodEntity> findByFoodNameContaining(String keyword);
    List<FoodEntity> findByRestAreaIdAndIsSignature(Integer restAreaId, String isSignature);
    List<FoodEntity> findByPriceLessThanEqual(double maxPrice);
}