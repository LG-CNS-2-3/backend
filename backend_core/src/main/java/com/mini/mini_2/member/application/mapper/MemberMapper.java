package com.mini.mini_2.member.application.mapper;

import com.mini.mini_2.member.application.dto.response.MemberResponse;
import com.mini.mini_2.member.domain.Member;
import org.springframework.stereotype.Component;

/**
 * Member <-> DTO 변환 Mapper
 */
@Component
public class MemberMapper {

    public MemberResponse toResponse(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getEmail().getValue(),
            member.getNickname().getValue(),
            member.getCreatedAt()
        );
    }
}
