package com.mini.mini_2.user.infrastructure.persistence.jpa; // [수정] 패키지 변경

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository; // [수정] @Repository는 Adapter로 이동

import com.mini.mini_2.user.domain.entity.UserEntity; // [수정] Entity 임포트 경로 변경

// [수정] 기존 UserRepository -> SpringDataUserRepository로 이름 변경
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Integer> {
    
    // [수정] 반환 타입을 Optional<UserEntity> 대신 UserEntity로 유지 (Adapter에서 처리)
    UserEntity findByUserEmailAndPassword(String userEmail, String password);
    UserEntity findByUserEmail(String userEmail);
}