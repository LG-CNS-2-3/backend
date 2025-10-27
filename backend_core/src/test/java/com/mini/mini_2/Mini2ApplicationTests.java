package com.mini.mini_2;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@ActiveProfiles("test")                       // 1. "application-test.yml"을 강제 사용
@EntityScan(basePackages = "com.mini.mini_2") // 2. "com.mini.mini_2" 하위의 모든 @Entity 스캔
@SpringBootTest
class Mini2ApplicationTests {

	@Test
	void contextLoads() {
	}

}