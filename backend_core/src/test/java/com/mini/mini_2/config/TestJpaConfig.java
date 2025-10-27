package com.mini.mini_2.config;

// import org.springframework.context.annotation.ComponentScan; // 이건 없어야 합니다.
// import org.springframework.context.annotation.FilterType; // 이것도 필요 없습니다.
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("test")

@EntityScan(basePackages = { // 엔티티 스캔은 그대로 둡니다.
    "com.mini.mini_2.rest_area.domain.entity",
    "com.mini.mini_2.facility.domain.entity",
    "com.mini.mini_2.food.domain.entity",
    "com.mini.mini_2.review.domain.entity",
    "com.mini.mini_2.favorite.domain.entity"

})
// @ComponentScan(...) // 이것도 없어야 합니다.
public class TestJpaConfig {
    // 내용은 비워둡니다.
}