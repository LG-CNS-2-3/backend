package com.mini.mini_2.food.application;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import com.mini.mini_2.exception.RestAreaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.mini_2.food.application.dto.FoodRequestDTO;
import com.mini.mini_2.food.application.dto.FoodResponseDTO;
import com.mini.mini_2.food.domain.FoodRepository;
import com.mini.mini_2.food.domain.entity.FoodEntity;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.domain.RestAreaRepository;

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

    // 일부 조회
    public FoodResponseDTO findByFoodId(Integer foodId) {
        return foodRepository.findById(foodId)
                .map(FoodResponseDTO::fromEntity)
                .orElse(null);
    }

    // 메뉴 수정
    public FoodResponseDTO update(Integer foodId, FoodRequestDTO request) {

        Optional<FoodEntity> foodEntity = foodRepository.findById(foodId);

        // RestAreaEntity fixedRestArea = existing.getRestArea();

        FoodEntity entity = foodEntity.get();
        entity.setFoodName(request.getFoodName());
        entity.setIsSignature(request.getIsSignature());
        entity.setPrice(request.getPrice());
        entity.setDescription(request.getDescription());

        FoodEntity saved = foodRepository.save(entity);

        return FoodResponseDTO.fromEntity(saved);
    }

    // 메뉴 삭제
    public boolean delete(Integer foodId) {

        FoodEntity entity = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("음식이 존재하지 않습니다. ID: " + foodId));

        foodRepository.delete(entity);

        return true;
    }

    // [수정] 메뉴 필터를 통한 음식 조회 (DB에서 직접 필터링)
    public List<FoodResponseDTO> searchByName(String keyword) {
        // [수정] findAll() 대신 쿼리 메서드 사용
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
        List<FoodEntity> entities = foodRepository.findByPriceLessThanEqual(maxPrice);

        return entities.stream()
                .map(FoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}


