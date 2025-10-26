package com.mini.mini_2.food.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // [추가]
import org.springframework.data.repository.query.Param; // [추가]

import com.mini.mini_2.food.domain.entity.FoodEntity;

import java.util.List; // [추가]

public interface SpringDataFoodRepository extends JpaRepository<FoodEntity, Integer> {

    // [수정] searchByName을 위한 쿼리 메서드
    List<FoodEntity> findByFoodNameContaining(String keyword);

    // [수정] searchByRestAreaId을 위한 쿼리 메서드
    // (RestAreaEntity의 restAreaId 필드와 isSignature 필드를 AND 조건으로 검색)
    List<FoodEntity> findByRestArea_RestAreaIdAndIsSignature(Integer restAreaId, String isSignature);

    // [수정] searchByPrice를 위한 쿼리 메서드
    // (price 필드가 String이므로, DB에서 double로 형변환하여 비교)
    @Query("SELECT f FROM FoodEntity f WHERE CAST(f.price AS double) <= :maxPrice")
    List<FoodEntity> findByPriceLessThanEqual(@Param("maxPrice") double maxPrice);
}