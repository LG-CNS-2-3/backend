package com.mini.mini_2.member.presentation;

import com.mini.mini_2.member.application.MemberApplicationService;
import com.mini.mini_2.member.application.dto.command.LoginCommand;
import com.mini.mini_2.member.application.dto.command.RegisterMemberCommand;
import com.mini.mini_2.member.application.dto.command.UpdateMemberCommand;
import com.mini.mini_2.member.application.dto.response.LoginResponse;
import com.mini.mini_2.member.application.dto.response.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member API", description = "회원 API")
public class MemberController {

    private final MemberApplicationService memberApplicationService;

    @Operation(summary = "회원가입", description = "새로운 회원을 등록합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/register")
    public ResponseEntity<MemberResponse> register(@RequestBody RegisterMemberCommand command) {
        MemberResponse response = memberApplicationService.register(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginCommand command) {
        LoginResponse response = memberApplicationService.login(command);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃", description = "액세스 토큰을 무효화합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            memberApplicationService.logout(token);
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "토큰 리프레시", description = "리프레시 토큰으로 새로운 토큰을 발급받습니다.")
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LoginResponse response = memberApplicationService.refresh(refreshToken);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원정보 수정", description = "회원의 닉네임과 비밀번호를 수정합니다.")
    @PutMapping("/profile")
    public ResponseEntity<MemberResponse> updateProfile(@RequestBody UpdateMemberCommand command) {
        MemberResponse response = memberApplicationService.updateProfile(command);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 탈퇴", description = "회원을 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<Void> withdraw(@RequestParam String email) {
        memberApplicationService.withdraw(email);
        return ResponseEntity.noContent().build();
    }
}
