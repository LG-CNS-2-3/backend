package com.mini.mini_2.member.infrastructure.persistence;

import com.mini.mini_2.member.domain.Favorite;
import com.mini.mini_2.member.domain.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final FavoriteJpaRepository jpaRepository;

    @Override
    public Favorite save(Favorite favorite) {
        return jpaRepository.save(favorite);
    }

    @Override
    public Optional<Favorite> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Favorite> findByMemberId(Long memberId) {
        return jpaRepository.findByMemberId(memberId);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
