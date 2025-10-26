package com.mini.mini_2.food.domain;

import java.util.List;
import java.util.Optional;
import com.mini.mini_2.food.domain.entity.FoodEntity;

/**
 * Food 도메인 리포지토리 인터페이스 (Application 계층이 의존하는 추상화된 포트)
 */
public interface FoodRepository {
    
    FoodEntity save(FoodEntity e);
    Optional<FoodEntity> findById(Integer id);
    List<FoodEntity> findAll();
    void delete(FoodEntity e);

    // 참고: 서비스 계층이 findAll() 후 인메모리 필터링을 하고 있으므로,
    // 이 인터페이스에는 기본 CRUD 메서드만 정의합니다.
}