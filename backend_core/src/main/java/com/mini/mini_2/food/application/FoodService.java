package com.mini.mini_2.food.application;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import com.mini.mini_2.exception.RestAreaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mini.mini_2.food.application.dto.FoodRequestDTO;
import com.mini.mini_2.food.application.dto.FoodResponseDTO;
import com.mini.mini_2.food.domain.FoodRepository;
import com.mini.mini_2.food.domain.entity.FoodEntity;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.domain.RestAreaRepository;

import com.mini.mini_2.exception.FoodNotFoundException;
import com.mini.mini_2.exception.RestAreaNotFoundException;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestAreaRepository restAreaRepository;

    // 음식 생성
    public FoodResponseDTO create(FoodRequestDTO request) {
        System.out.println("[FoodService] create : " + request); 

        RestAreaEntity restAreaEntity = restAreaRepository.findById(request.getRestAreaId())
                .orElseThrow(() -> new RestAreaNotFoundException(request.getRestAreaId()));

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

    // food id 기반 조회
public FoodResponseDTO findByFoodId(Integer foodId) {
        System.out.println("[FoodService] findByFoodId: " + foodId);
        // [수정] orElseThrow로 예외 처리 후 바로 DTO 변환 (코드 간결화)
        FoodEntity foodEntity = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodNotFoundException(foodId));
        return FoodResponseDTO.fromEntity(foodEntity); // 찾은 엔티티를 DTO로 변환하여 반환
    }

    // 메뉴 수정
    public FoodResponseDTO update(Integer foodId, FoodRequestDTO request) {

        System.out.println("[FoodService] update foodId: " + foodId + ", request: " + request);
        // [수정] orElseThrow 사용하여 수정할 음식이 없을 경우 FoodNotFoundException 발생
        FoodEntity entity = foodRepository.findById(foodId)
                 .orElseThrow(() -> new FoodNotFoundException(foodId));
    

        // RestAreaEntity fixedRestArea = existing.getRestArea();

        boolean changed = false;
        if (request.getFoodName() != null && !request.getFoodName().isBlank()) {
            entity.setFoodName(request.getFoodName());
            changed = true;
        }
        if (request.getPrice() != null && !request.getPrice().isBlank()) {
            entity.setPrice(request.getPrice());
            changed = true;
        }
        if (request.getIsSignature() != null && !request.getIsSignature().isBlank()) {
            entity.setIsSignature(request.getIsSignature());
            changed = true;
        }
        if (request.getDescription() != null) { // 설명은 비어있을 수 있다고 가정
            entity.setDescription(request.getDescription());
            changed = true;
        }

        // 변경된 경우에만 저장 (선택적 최적화)
        FoodEntity saved = entity; // 기본값
        if (changed) {
            saved = foodRepository.save(entity);
        }

        return FoodResponseDTO.fromEntity(saved);
    }

    // 메뉴 삭제
    @Transactional
    public boolean delete(Integer foodId) {

        FoodEntity entity = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodNotFoundException(foodId));

        foodRepository.delete(entity);

        return true;
    }

    // [수정] 메뉴 필터를 통한 음식 조회 (DB에서 직접 필터링)
    public List<FoodResponseDTO> searchByName(String keyword) {
        System.out.println("[FoodService] searchByName: " + keyword);
        List<FoodEntity> entities = foodRepository.findByFoodNameContaining(keyword);

        return entities.stream()
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // [수정] 대표 메뉴 필터를 통한 음식 조회 (DB에서 직접 필터링)
    public List<FoodResponseDTO> searchByRestAreaId(Integer restAreaId) {
        // [수정] findAll() 대신 쿼리 메서드 사용 (시그니처 "Y"를 인자로 전달)
        List<FoodEntity> entities = foodRepository.findByRestAreaIdAndIsSignature(restAreaId, "Y");

        return entities.stream()
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // [수정] 가격 필터를 통한 음식 조회 (DB에서 직접 필터링)
    public List<FoodResponseDTO> searchByPrice(double maxPrice) {
        // [수정] findAll() 대신 쿼리 메서드 사용
        System.out.println("[FoodService] searchByPrice <= " + maxPrice);
        List<FoodEntity> entities = foodRepository.findByPriceLessThanEqual(maxPrice);

        return entities.stream()
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}


