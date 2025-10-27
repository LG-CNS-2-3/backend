package com.mini.mini_2.member.infrastructure.persistence;

import com.mini.mini_2.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailValue(String email);
    
    boolean existsByEmailValue(String email);
}
