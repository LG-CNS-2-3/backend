package com.mini.mini_2.food.application; // [수정] 패키지 변경

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// [수정] DTO 임포트 경로 변경
import com.mini.mini_2.food.application.dto.FoodRequestDTO;
import com.mini.mini_2.food.application.dto.FoodResponseDTO;
// [수정] Entity 임포트 경로 변경
import com.mini.mini_2.food.domain.entity.FoodEntity;
// [핵심 수정] Infrastructure의 JPA 인터페이스가 아닌 Domain의 Repository 인터페이스를 임포트
import com.mini.mini_2.food.domain.FoodRepository; 
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.domain.RestAreaRepository;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository; // [수정] 이제 이 인터페이스는 Domain 계층의 것임

    @Autowired
    private RestAreaRepository restAreaRepository;

    // (내부 로직은 기존과 동일)
    
    // 음식 생성
    public FoodResponseDTO create(FoodRequestDTO request) {
        System.out.println("[FoodService] create : " + request); 

        RestAreaEntity restAreaEntity = restAreaRepository.findById(request.getRestAreaId()).get();
        
        FoodEntity foodEntity = request.toEntity(restAreaEntity);

        foodRepository.save(foodEntity);

        return FoodResponseDTO.fromEntity(foodEntity);
    }

    // 전체 조회
    public List<FoodResponseDTO> findAll() {
        return foodRepository.findAll()
                .stream()
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    // ... (이하 모든 서비스 메서드 로직 동일) ...
    
    // 가격 필터를 통한 음식 조회
    public List<FoodResponseDTO> searchByPrice(double maxPrice) {
        return foodRepository.findAll().stream()
                .filter(f -> {double price = Double.parseDouble(f.getPrice());
                    return price <= maxPrice;})
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}