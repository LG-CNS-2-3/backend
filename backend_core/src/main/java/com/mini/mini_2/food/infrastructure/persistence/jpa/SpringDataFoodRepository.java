package com.mini.mini_2.food.infrastructure.persistence.jpa; // [수정] 패키지 변경

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository; // [수정] @Repository 어노테이션은 Adapter로 이동

import com.mini.mini_2.food.domain.entity.FoodEntity; // [수정] Entity 임포트 경로 변경

// [수정] 기존 FoodRepository -> SpringDataFoodRepository로 이름 변경
public interface SpringDataFoodRepository extends JpaRepository<FoodEntity, Integer> {
    
    // 서비스 계층이 모두 findAll()을 기반으로 동작하므로
    // 별도의 쿼리 메서드가 필요하지 않습니다. (기존 로직 유지)
}