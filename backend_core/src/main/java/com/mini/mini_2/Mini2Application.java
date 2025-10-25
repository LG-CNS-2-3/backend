package com.mini.mini_2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mini.mini_2")  // JPA 리포+어댑터 전부 스캔
@EntityScan(basePackages = "com.mini.mini_2")             // 엔티티 전부 스캔
public class Mini2Application {
	public static void main(String[] args) {
		SpringApplication.run(Mini2Application.class, args);
	}
	
	@Bean
	CommandLineRunner redisSmokeTest(RedisTemplate<String, Object> redisTemplate) {
		return args -> {
			try {
				redisTemplate.opsForValue().set("health:ping", "pong");
				Object v = redisTemplate.opsForValue().get("health:ping");
				System.out.println("[Redis OK] value=" + v);
			} catch (Exception e) {
				System.err.println("[Redis ERR] " + e.getMessage());
			}
		};
	}
}