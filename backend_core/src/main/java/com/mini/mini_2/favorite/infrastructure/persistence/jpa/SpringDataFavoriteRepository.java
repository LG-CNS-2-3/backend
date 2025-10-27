package com.mini.mini_2.favorite.infrastructure.persistence.jpa; // [수정] 패키지 변경

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository; // [수정] @Repository는 Adapter로 이동

import com.mini.mini_2.favorite.domain.entity.FavoriteEntity; // [수정] Entity 임포트 경로 변경

// [수정] 기존 FavoriteRepository -> SpringDataFavoriteRepository로 이름 변경
public interface SpringDataFavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

    // (내부 로직은 기존과 동일)
    List<FavoriteEntity> findAllByUser_UserId(Integer userId);
}