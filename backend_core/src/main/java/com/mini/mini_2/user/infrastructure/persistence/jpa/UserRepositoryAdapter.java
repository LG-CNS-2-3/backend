package com.mini.mini_2.user.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mini.mini_2.user.domain.UserRepository;
import com.mini.mini_2.user.domain.entity.UserEntity;

import lombok.RequiredArgsConstructor;

/**
 * 도메인 리포지토리(UserRepository)의 Spring Data JPA 구현체(어댑터)
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository jpa;

    @Override
    public UserEntity save(UserEntity e) {
        return jpa.save(e);
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        return jpa.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUserEmailAndPassword(String userEmail, String password) {
        // [수정] SpringData 인터페이스의 반환 타입에 맞춰 Optional.ofNullable 사용
        return Optional.ofNullable(jpa.findByUserEmailAndPassword(userEmail, password));
    }

    @Override
    public Optional<UserEntity> findByUserEmail(String userEmail) {
        // [수정] SpringData 인터페이스의 반환 타입에 맞춰 Optional.ofNullable 사용
        return Optional.ofNullable(jpa.findByUserEmail(userEmail));
    }

    @Override
    public void deleteById(Integer id) {
        jpa.deleteById(id);
    }
}