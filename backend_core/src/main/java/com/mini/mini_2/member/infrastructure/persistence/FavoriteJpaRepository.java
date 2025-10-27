package com.mini.mini_2.member.infrastructure.persistence;

import com.mini.mini_2.member.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface FavoriteJpaRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByMemberId(Long memberId);
}
