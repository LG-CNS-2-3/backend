package com.mini.mini_2.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Gateway에서 전달된 X-User-Id 헤더를 읽어서 request attribute에 저장
 * Gateway에서 JWT 검증을 완료하고 전달하므로, 여기서는 단순히 헤더만 읽음
 */
@Component
public class UserIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Gateway에서 추가한 X-User-Id 헤더 읽기
        String userId = request.getHeader("X-User-Id");

        if (userId != null) {
            // request attribute에 저장 (Controller에서 사용 가능)
            request.setAttribute("userId", userId);
        }

        return true;
    }
}
