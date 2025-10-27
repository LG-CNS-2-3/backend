package com.mini.mini_2.favorite.application; // [수정] 패키지 변경

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// [수정] DTO 임포트 경로 변경
import com.mini.mini_2.favorite.application.dto.FavoriteRequestDTO;
import com.mini.mini_2.favorite.application.dto.FavoriteResponseDTO;
// [수정] Entity 임포트 경로 변경
import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;
// [핵심 수정] Domain 계층의 Repository 인터페이스들을 임포트
import com.mini.mini_2.favorite.domain.FavoriteRepository;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.domain.RestAreaRepository;
import com.mini.mini_2.user.domain.UserRepository;
import com.mini.mini_2.user.domain.entity.UserEntity;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestAreaRepository restAreaRepository;
    
    // 즐겨찾기 생성
    public FavoriteResponseDTO create(FavoriteRequestDTO request) {
        System.out.println("[FavoriteService] create : " +request);
        
        Optional<UserEntity> userEntity = userRepository.findById(request.getUserId());
        Optional<RestAreaEntity> restAreaEntity = restAreaRepository.findById(request.getRestAreaId());
        

        FavoriteEntity entity = favoriteRepository.save(request.toEntity(userEntity.get(), restAreaEntity.get()));
        return FavoriteResponseDTO.fromEntity(entity);
    }
    
    // 즐겨찾기 삭제
    public void delete(Integer favoriteId) {
        System.out.println("[FavoriteService] delete : "+ favoriteId);
        
        favoriteRepository.deleteById(favoriteId);
        
    }

    // ID 기반 즐겨찾기 단건 조회
    public List<FavoriteResponseDTO> findByUserId(Integer userId) {
        System.out.println("[FavoriteService] findByUserId : "+ userId);

        List<FavoriteEntity> entities = favoriteRepository.findAllByUser_UserId(userId);
        
        return entities.stream()
                       .map(entity -> FavoriteResponseDTO.fromEntity(entity))
                       .toList();
        
    }
}
