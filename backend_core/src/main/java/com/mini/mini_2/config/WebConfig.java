package com.mini.mini_2.config;

import com.mini.mini_2.auth.UserIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 설정
 * Gateway에서 JWT 인증을 처리하므로 AuthInterceptor 제거됨
 * UserIdInterceptor는 Gateway에서 전달한 X-User-Id 헤더를 읽기만 함
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserIdInterceptor userIdInterceptor;

    public WebConfig(UserIdInterceptor userIdInterceptor) {
        this.userIdInterceptor = userIdInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Gateway에서 전달한 X-User-Id 헤더를 request attribute에 저장
        registry.addInterceptor(userIdInterceptor)
                .addPathPatterns("/**");
    }
}
