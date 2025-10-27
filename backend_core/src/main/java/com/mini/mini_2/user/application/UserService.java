package com.mini.mini_2.user.application; // [수정] 패키지 변경

import java.util.Optional; // [수정] Optional 사용

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// [수정] DTO 임포트 경로 변경
import com.mini.mini_2.user.application.dto.UserRequestDTO;
import com.mini.mini_2.user.application.dto.UserResponseDTO;
// [수정] Entity 임포트 경로 변경
import com.mini.mini_2.user.domain.entity.UserEntity;
// [핵심 수정] Domain 계층의 Repository 인터페이스 임포트
import com.mini.mini_2.user.domain.UserRepository; 

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository; // [수정] Domain 인터페이스
    
    // 회원 가입
    public UserResponseDTO create(UserRequestDTO request) {
        System.out.println("[UserService] create : " +request);
        
        UserEntity entity = userRepository.save(request.toEntity());
        return UserResponseDTO.fromEntity(entity);
    }
    
    // 회원 로그인
    public UserResponseDTO login(UserRequestDTO request) {
        System.out.println("[UserService] login");
        
        // [수정] Optional 처리 방식 사용
        Optional<UserEntity> entityOptional = userRepository.findByUserEmailAndPassword(request.getUserEmail(), request.getPassword());
        
        // Optional이 비어있으면 null 반환, 아니면 DTO로 변환하여 반환
        return entityOptional.map(UserResponseDTO::fromEntity).orElse(null);
    }
    
    // 회원 정보 수정
    public UserResponseDTO update(UserRequestDTO request) {
        System.out.println("[UserService] update user info");
        
        // [수정] Optional 처리 및 예외 처리 추가 (이메일이 없는 경우 등)
        UserEntity entity = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getUserEmail()));

        entity.setUserNickname(request.getUserNickname());
        entity.setPassword(request.getPassword()); // 비밀번호 변경 로직은 보안상 해싱 필요

        return UserResponseDTO.fromEntity(userRepository.save(entity));
    }
    
    // 회원 정보 삭제
    public Integer delete(UserRequestDTO request) {
        System.out.println("[UserService] delete user");
        
        // [수정] Optional 처리 방식 사용
        Optional<UserEntity> entityOptional = userRepository.findByUserEmail(request.getUserEmail());
        
        if (entityOptional.isEmpty()) {
            return 0; // 삭제할 유저 없음
        }
        
        userRepository.deleteById(entityOptional.get().getUserId());
        
        return 1; // 삭제 성공
        
    }
}