package com.mini.mini_2.member.application;

import com.mini.mini_2.member.application.dto.command.LoginCommand;
import com.mini.mini_2.member.application.dto.command.RegisterMemberCommand;
import com.mini.mini_2.member.application.dto.command.UpdateMemberCommand;
import com.mini.mini_2.member.application.dto.response.LoginResponse;
import com.mini.mini_2.member.application.dto.response.MemberResponse;
import com.mini.mini_2.member.application.mapper.MemberMapper;
import com.mini.mini_2.member.domain.Member;
import com.mini.mini_2.member.domain.MemberRepository;
import com.mini.mini_2.member.domain.exception.DuplicateEmailException;
import com.mini.mini_2.member.domain.exception.InvalidCredentialsException;
import com.mini.mini_2.member.domain.exception.MemberNotFoundException;
import com.mini.mini_2.member.domain.service.PasswordEncoder;
import com.mini.mini_2.member.domain.vo.Email;
import com.mini.mini_2.member.domain.vo.Nickname;
import com.mini.mini_2.member.domain.vo.Password;
import com.mini.mini_2.member.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberApplicationService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final MemberMapper mapper;

    /**
     * 회원가입
     */
    public MemberResponse register(RegisterMemberCommand command) {
        // String -> 값 객체 변환 (자동 검증)
        Email email = new Email(command.email());
        Password rawPassword = new Password(command.password());
        Nickname nickname = new Nickname(command.nickname());

        // 이메일 중복 체크
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email.getValue());
        }

        // 비밀번호 암호화
        Password encodedPassword = passwordEncoder.encode(rawPassword);

        // 도메인 객체 생성
        Member member = Member.builder()
            .email(email)
            .password(encodedPassword)
            .nickname(nickname)
            .build();

        // 저장
        Member savedMember = memberRepository.save(member);

        // DTO 변환
        return mapper.toResponse(savedMember);
    }

    /**
     * 로그인
     */
    public LoginResponse login(LoginCommand command) {
        // 값 객체 변환
        Email email = new Email(command.email());
        Password rawPassword = new Password(command.password());

        // 회원 조회
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(InvalidCredentialsException::new);

        // 비밀번호 검증
        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // 토큰 생성
        String accessToken = tokenProvider.generateAccessToken(member.getId());
        String refreshToken = tokenProvider.generateRefreshToken(member.getId());

        // 응답 생성
        return new LoginResponse(
            mapper.toResponse(member),
            accessToken,
            refreshToken
        );
    }

    /**
     * 회원정보 수정
     */
    public MemberResponse updateProfile(UpdateMemberCommand command) {
        Email email = new Email(command.email());
        Nickname newNickname = new Nickname(command.nickname());
        Password rawPassword = new Password(command.password());

        Member member = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);

        Password encodedPassword = passwordEncoder.encode(rawPassword);

        member.updateProfile(newNickname, encodedPassword); // 도메인 메서드 호출

        Member updated = memberRepository.save(member);

        return mapper.toResponse(updated);
    }

    /**
     * 회원 탈퇴
     */
    public void withdraw(String emailString) {
        Email email = new Email(emailString);
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);

        memberRepository.deleteById(member.getId());
    }

    /**
     * 로그아웃
     */
    public void logout(String token) {
        tokenProvider.invalidateAccessToken(token);
    }

    /**
     * 토큰 리프레시
     */
    public LoginResponse refresh(String refreshToken) {
        var tokenPair = tokenProvider.refreshWithRotation(refreshToken);
        if (tokenPair == null) {
            throw new InvalidCredentialsException();
        }

        Long memberId = tokenProvider.getMemberIdFromToken(tokenPair.accessToken());
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        return new LoginResponse(
            mapper.toResponse(member),
            tokenPair.accessToken(),
            tokenPair.refreshToken()
        );
    }
}
