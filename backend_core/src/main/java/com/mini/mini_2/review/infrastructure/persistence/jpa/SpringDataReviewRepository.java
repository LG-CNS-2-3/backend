package com.mini.mini_2.review.infrastructure.persistence.jpa; // [수정] 패키지 변경

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository; // [수정] @Repository는 Adapter로 이동

import com.mini.mini_2.review.domain.entity.ReviewEntity; // [수정] Entity 임포트 경로 변경

// [수정] 기존 ReviewRepository -> SpringDataReviewRepository로 이름 변경
public interface SpringDataReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    
    // (내부 로직은 기존과 동일)

    List<ReviewEntity> findByUser_UserId(Integer userId);

    // 최신순(정렬)
    List<ReviewEntity> findByRestArea_RestAreaIdOrderByCreatedAtDesc(Integer restAreaId);
    // 평점순(정렬)
    List<ReviewEntity> findByRestArea_RestAreaIdOrderByRatingDesc(Integer restAreaId);

    // [참고] 기존에 있던 findByRestArea_RestAreaId는 서비스에서 사용되지 않아 
    // Domain 인터페이스에서 제외되었으나, 여기에 남겨두어도 무방합니다.
    // List<ReviewEntity> findByRestArea_RestAreaId(Integer restAreaId); 
}