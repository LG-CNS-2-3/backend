package com.mini.mini_2.all;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mini.mini_2.facility.application.dto.FacilityRequestDTO;
import com.mini.mini_2.facility.application.dto.FacilityResponseDTO;
import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.facility.domain.FacilityRepository;
import com.mini.mini_2.favorite.application.dto.FavoriteRequestDTO;
import com.mini.mini_2.favorite.application.dto.FavoriteResponseDTO;
import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;
import com.mini.mini_2.favorite.domain.FavoriteRepository;
import com.mini.mini_2.food.application.dto.FoodRequestDTO;
import com.mini.mini_2.food.application.dto.FoodResponseDTO;
import com.mini.mini_2.food.domain.FoodRepository;
import com.mini.mini_2.food.domain.entity.FoodEntity;
import com.mini.mini_2.rest_area.application.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.application.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.domain.RestAreaRepository;
import com.mini.mini_2.review.application.dto.ReviewRequestDTO;
import com.mini.mini_2.review.application.dto.ReviewResponseDTO;
import com.mini.mini_2.review.domain.ReviewRepository;
import com.mini.mini_2.review.domain.entity.ReviewEntity;
import com.mini.mini_2.user.application.dto.UserRequestDTO;
import com.mini.mini_2.user.application.dto.UserResponseDTO;
import com.mini.mini_2.user.domain.entity.UserEntity;
import com.mini.mini_2.user.domain.UserRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@ActiveProfiles("test")                       // 1. "application-test.yml"을 강제 사용
@EntityScan(basePackages = "com.mini.mini_2") // 2. "com.mini.mini_2" 하위의 모든 @Entity 스캔

@SpringBootTest
@Transactional
@Rollback
public class TestAllTables {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RestAreaRepository restAreaRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FacilityRepository facilityRepository;

    @Test
    public void test () {
        
        // user
        UserRequestDTO userRequest = UserRequestDTO.builder()
                                            .userEmail("hnn@naver.com")
                                            .userNickname("hna12")
                                            .password("23125")
                                            .build();
                                            
        System.out.println("user request : " + userRequest);
                                            

        UserEntity userEntity = userRepository.save(userRequest.toEntity());
        UserResponseDTO userResponse = UserResponseDTO.fromEntity(userEntity);       
        
        System.out.println("user entity : " +  userEntity);
        System.out.println("user dto : " +  userResponse);

        
        // restarea
        RestAreaRequestDTO restAreaRequest = RestAreaRequestDTO.builder()
                                                               .name("덕평휴게소")
                                                               .direction("상행")
                                                               .code("000015")
                                                               .tel("01012345679")
                                                               .address("경기도 어딘가")
                                                               .routeName("영동고속도로")
                                                               .build();
                                                               
        System.out.println("rest area request : " + restAreaRequest);
                                                               
        RestAreaEntity restAreaEntity = restAreaRepository.save(restAreaRequest.toEntity());
        RestAreaResponseDTO restAreaResponse = RestAreaResponseDTO.fromEntity(restAreaEntity);
        System.out.println("rest area entity : " + restAreaEntity);
        System.out.println("rest area dto : " + restAreaResponse);
        
        // food
        FoodRequestDTO foodRequest = FoodRequestDTO.builder()
                                                      .foodName("치킨")
                                                      .restAreaId(restAreaResponse.getRestAreaId())
                                                      .price("1000")
                                                      .isSignature("true")
                                                      .description("겉바속촉")
                                                      .build();
        FoodEntity foodEntity = foodRepository.save(foodRequest.toEntity(restAreaEntity));
        FoodResponseDTO foodResponse = FoodResponseDTO.fromEntity(foodEntity);
        System.out.println("food entity : " + foodEntity);
        System.out.println("food dto : " + foodResponse);
        
        // facility
        FacilityRequestDTO facilityRequest = FacilityRequestDTO.builder()
                                                               .name("전기차충전소")
                                                               .restAreaId(restAreaResponse.getRestAreaId())
                                                               .description("20분에 풀충")
                                                               .build();
        FacilityEntity facilityEntity = facilityRepository.save(facilityRequest.toEntity(restAreaEntity));
        FacilityResponseDTO facilityResponse = FacilityResponseDTO.fromEntity(facilityEntity);
        System.out.println("facility entity : " + facilityEntity);
        System.out.println("facility dto : " + facilityResponse);
        
        // favorite
        FavoriteRequestDTO favoriteRequest = FavoriteRequestDTO.builder()
                                                               .userId(userResponse.getUserId())
                                                               .restAreaId(restAreaResponse.getRestAreaId())
                                                               .description("호두과자가 기막힘")
                                                               .build();
        FavoriteEntity favoriteEntity = favoriteRepository.save(favoriteRequest.toEntity(userEntity, restAreaEntity));
        FavoriteResponseDTO favoriteResponse = FavoriteResponseDTO.fromEntity(favoriteEntity);
        System.out.println("favorite entity : " + favoriteEntity);
        System.out.println("favorite dto : " + favoriteResponse);
        
        // review
        ReviewRequestDTO reviewRequest = ReviewRequestDTO.builder()
                                                         .userId(userResponse.getUserId())
                                                         .restAreaId(restAreaResponse.getRestAreaId())
                                                         .rating("4.5")
                                                         .comment("아주 편해용")
                                                         .build();
        ReviewEntity reviewEntity = reviewRepository.save(reviewRequest.toEntity(userEntity, restAreaEntity));
        ReviewResponseDTO reviewResponse = ReviewResponseDTO.fromEntity(reviewEntity);
        System.out.println("review entity : " + reviewEntity);
        System.out.println("review dto : " + reviewResponse);
    }
    
    
}
