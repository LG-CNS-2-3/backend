package com.mini.mini_2.member.domain;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository {
    Favorite save(Favorite favorite);
    Optional<Favorite> findById(Long id);
    List<Favorite> findByMemberId(Long memberId);
    void deleteById(Long id);
}
