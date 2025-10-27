package com.mini.mini_2.member.application;

import com.mini.mini_2.member.application.dto.command.AddFavoriteCommand;
import com.mini.mini_2.member.application.dto.response.FavoriteResponse;
import com.mini.mini_2.member.application.mapper.FavoriteMapper;
import com.mini.mini_2.member.domain.Favorite;
import com.mini.mini_2.member.domain.FavoriteRepository;
import com.mini.mini_2.member.domain.Member;
import com.mini.mini_2.member.domain.MemberRepository;
import com.mini.mini_2.member.domain.exception.MemberNotFoundException;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteApplicationService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final RestAreaRepository restAreaRepository;
    private final FavoriteMapper mapper;

    /**
     * 즐겨찾기 추가
     */
    public FavoriteResponse addFavorite(AddFavoriteCommand command) {
        Member member = memberRepository.findById(command.memberId())
            .orElseThrow(() -> new MemberNotFoundException(command.memberId()));

        RestAreaEntity restArea = restAreaRepository.findById(command.restAreaId())
            .orElseThrow(() -> new IllegalArgumentException("휴게소를 찾을 수 없습니다"));

        Favorite favorite = Favorite.create(member, restArea, command.description());

        Favorite saved = favoriteRepository.save(favorite);

        return mapper.toResponse(saved);
    }

    /**
     * 회원별 즐겨찾기 목록 조회
     */
    @Transactional(readOnly = true)
    public List<FavoriteResponse> getFavorites(Long memberId) {
        List<Favorite> favorites = favoriteRepository.findByMemberId(memberId);
        return favorites.stream()
            .map(mapper::toResponse)
            .toList();
    }

    /**
     * 즐겨찾기 삭제
     */
    public void removeFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
}
