package com.mini.mini_2.review.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mini.mini_2.review.domain.ReviewRepository;
import com.mini.mini_2.review.domain.entity.ReviewEntity;

import lombok.RequiredArgsConstructor;

/**
 * 도메인 리포지토리(ReviewRepository)의 Spring Data JPA 구현체(어댑터)
 */
@Repository
@RequiredArgsConstructor
public class ReviewRepositoryAdapter implements ReviewRepository {

    private final SpringDataReviewRepository jpa;

    @Override
    public ReviewEntity save(ReviewEntity e) {
        return jpa.save(e);
    }

    @Override
    public Optional<ReviewEntity> findById(Integer reviewId) {
        return jpa.findById(reviewId);
    }

    @Override
    public void deleteById(Integer reviewId) {
        jpa.deleteById(reviewId);
    }

    @Override
    public List<ReviewEntity> findByRestArea_RestAreaIdOrderByRatingDesc(Integer restAreaId) {
        return jpa.findByRestArea_RestAreaIdOrderByRatingDesc(restAreaId);
    }

    @Override
    public List<ReviewEntity> findByRestArea_RestAreaIdOrderByCreatedAtDesc(Integer restAreaId) {
        return jpa.findByRestArea_RestAreaIdOrderByCreatedAtDesc(restAreaId);
    }

    @Override
    public List<ReviewEntity> findByUser_UserId(Integer userId) {
        return jpa.findByUser_UserId(userId);
    }
}