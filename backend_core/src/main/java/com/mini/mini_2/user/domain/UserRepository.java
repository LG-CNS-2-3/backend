package com.mini.mini_2.user.domain;

import java.util.Optional;
import com.mini.mini_2.user.domain.entity.UserEntity;

/**
 * User 도메인 리포지토리 인터페이스 (Application 계층이 의존하는 포트)
 */
public interface UserRepository {
    
    UserEntity save(UserEntity e);
    
    Optional<UserEntity> findById(Integer id); // 다른 서비스에서 필요할 수 있음
    
    Optional<UserEntity> findByUserEmailAndPassword(String userEmail, String password);
    
    Optional<UserEntity> findByUserEmail(String userEmail);
    
    void deleteById(Integer id);
}