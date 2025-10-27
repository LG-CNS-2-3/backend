package com.mini.mini_2.member.domain;

import com.mini.mini_2.member.domain.vo.Email;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(Email email);
    boolean existsByEmail(Email email);
    void deleteById(Long id);
}
