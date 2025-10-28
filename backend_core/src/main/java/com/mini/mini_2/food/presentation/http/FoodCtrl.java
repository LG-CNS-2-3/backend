package com.mini.mini_2.food.presentation.http;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; // [수정] PutMapping 임포트
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mini.mini_2.exception.RestAreaNotFoundException;
import com.mini.mini_2.food.application.FoodService;
import com.mini.mini_2.food.application.dto.FoodRequestDTO;
import com.mini.mini_2.food.application.dto.FoodResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.mini.mini_2.rest_area.domain.RestAreaRepository;
import com.mini.mini_2.exception.RestAreaNotFoundException;



@RestController
@RequestMapping("/api/v1/mini/food")
@Tag(name = "Food API", description = "음식 API")
public class FoodCtrl {

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestAreaRepository restAreaRepository;

    @Operation(
        summary = "휴게소 음식 생성",
        description = "휴게소 음식을 생성해주세요."
    )
    @PostMapping("/create")
    public ResponseEntity<FoodResponseDTO> create(@RequestBody FoodRequestDTO request) {
        // [수정] try-catch 제거
        // 예외가 발생하면 GlobalExceptionHandler가 처리
        System.out.println("[FoodCtrl] create : " + request);
        FoodResponseDTO response = foodService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "음식 목록 전체 조회",
        description = "휴게소 음식 전체 목록입니다."
    )
    @GetMapping("/lists")
    public ResponseEntity<List<FoodResponseDTO>> findAll() {
        List<FoodResponseDTO> responses = foodService.findAll();
        return ResponseEntity.ok(responses);
    }

    @Operation(
        summary = "음식 ID 기반 음식 조회",
        description = "음식 ID를 입력해주세요."
    )
    @GetMapping("/lists/{foodId}")
    public ResponseEntity<FoodResponseDTO> findByFoodId(@PathVariable("foodId") Integer foodId) {
        // [수정] if (response == null) 제거
        // FoodService.findByFoodId가 예외를 던지므로(orElseThrow), null이 올 수 없음
        FoodResponseDTO response = foodService.findByFoodId(foodId);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "음식 정보 수정",
        description = "음식 ID를 입력해주세요."
    )
    @PutMapping("/update/{foodId}") // [수정] PostMapping -> PutMapping
    public ResponseEntity<FoodResponseDTO> update(
            @PathVariable("foodId") Integer foodId,
            @RequestBody FoodRequestDTO request) {

        // [수정] try-catch 제거
        FoodResponseDTO response = foodService.update(foodId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "음식 정보 삭제",
        description = "음식 ID를 입력해주세요."
    )
    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<Void> delete(
            @PathVariable("foodId") Integer foodId) {
        
        // 이 코드는 이미 예외를 던지도록 되어있어 완벽합니다.
        foodService.delete(foodId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(
        summary = "메뉴 기반 음식 목록 조회",
        description = "음식 메뉴를 입력해주세요."
    )
    @GetMapping("/search/{name}")
    public ResponseEntity<List<FoodResponseDTO>> searchByName(@RequestParam("arg0") String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return ResponseEntity.ok(List.of()); // 빈 리스트 반환
        }
        
        List<FoodResponseDTO> responses = foodService.searchByName(keyword);

        // [수정] 검색 결과가 없는 것은 '에러(404)'가 아니라 '결과 없음(200 OK + 빈 리스트)'
        return ResponseEntity.ok(responses);
    }

    @Operation(
        summary = "휴게소 ID 기반 대표 메뉴 목록 조회",
        description = "휴게소 ID를 입력해주세요."
    )
    @GetMapping("/search/signature/{restAreaId}")
    public ResponseEntity<List<FoodResponseDTO>> searchByRestAreaId(
            @PathVariable("restAreaId") Integer restAreaId) {


         restAreaRepository.findById(restAreaId)
                .orElseThrow(() -> new RestAreaNotFoundException(restAreaId));

        List<FoodResponseDTO> responses = foodService.searchByRestAreaId(restAreaId);

        // [수정] 검색 결과가 없는 것은 '에러(404)'가 아니라 '결과 없음(200 OK + 빈 리스트)'
        return ResponseEntity.ok(responses);
    }

    @Operation(
        summary = "가격 기반 음식 목록 조회",
        description = "가격을 입력해주세요."
    )
    @GetMapping("/search/price/{price}")
    public ResponseEntity<List<FoodResponseDTO>> searchByPrice(@RequestParam("arg0") double maxPrice) {
        List<FoodResponseDTO> responses = foodService.searchByPrice(maxPrice);

        // [수정] 검색 결과가 없는 것은 '에러(404)'가 아니라 '결과 없음(200 OK + 빈 리스트)'
        return ResponseEntity.ok(responses);
    }
}