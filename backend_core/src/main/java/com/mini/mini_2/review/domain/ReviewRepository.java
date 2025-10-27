package com.mini.mini_2.review.domain;

import java.util.List;
import java.util.Optional;

import com.mini.mini_2.review.domain.entity.ReviewEntity;

/**
 * Review 도메인 리포지토리 인터페이스 (Application 계층이 의존하는 포트)
 */
public interface ReviewRepository {
    
    ReviewEntity save(ReviewEntity e);
    
    Optional<ReviewEntity> findById(Integer reviewId);
    
    void deleteById(Integer reviewId);

    List<ReviewEntity> findByRestArea_RestAreaIdOrderByRatingDesc(Integer restAreaId);

    List<ReviewEntity> findByRestArea_RestAreaIdOrderByCreatedAtDesc(Integer restAreaId);

    List<ReviewEntity> findByMember_Id(Long memberId);
}