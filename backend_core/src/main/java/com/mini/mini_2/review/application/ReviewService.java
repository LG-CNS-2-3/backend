package com.mini.mini_2.review.application; // [수정] 패키지 변경

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// [수정] DTO 임포트 경로 변경
import com.mini.mini_2.review.application.dto.ReviewRequestDTO;
import com.mini.mini_2.review.application.dto.ReviewResponseDTO;
// [수정] Entity 임포트 경로 변경
import com.mini.mini_2.review.domain.entity.ReviewEntity;
// [핵심 수정] Domain 계층의 Repository 인터페이스들을 임포트
import com.mini.mini_2.review.domain.ReviewRepository;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.domain.RestAreaRepository;
import com.mini.mini_2.member.domain.MemberRepository;
import com.mini.mini_2.member.domain.Member;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RestAreaRepository restAreaRepository;

    // 리뷰 작성
    public ReviewResponseDTO create(ReviewRequestDTO request) {
        System.out.println("[ReviewService] create : "+ request);

        Optional<Member> member = memberRepository.findById(Long.valueOf(request.getMemberId()));
        Optional<RestAreaEntity> restAreaEntity = restAreaRepository.findById(request.getRestAreaId());


        ReviewEntity entity = reviewRepository.save(request.toEntity(member.get(), restAreaEntity.get()));
        return ReviewResponseDTO.fromEntity(entity);
    }

    // ID 기반 리뷰 정렬 조회
    public List<ReviewResponseDTO> findByRestAreaId(Integer restAreaId, String sort) {
        System.out.println("[RestAreaService] findByRestAreaId : "+ restAreaId);
        System.out.println("[RestAreaService] sort : "+ sort);
        List<ReviewEntity> responses =
                ("평점순".equalsIgnoreCase(sort))
                        ? reviewRepository.findByRestArea_RestAreaIdOrderByRatingDesc(restAreaId)     
                        : reviewRepository.findByRestArea_RestAreaIdOrderByCreatedAtDesc(restAreaId);
        return responses.stream()
                        .map(ReviewResponseDTO::fromEntity)
                        .toList();
    }
    
    // ID 기반 회원 리뷰 조회
    public List<ReviewResponseDTO> findByUserId(Integer userId) {

        List<ReviewEntity> responses = reviewRepository.findByMember_Id(Long.valueOf(userId));

        return responses.stream()
                .map(entity -> ReviewResponseDTO.fromEntity(entity))
                .toList();
    }

    // 리뷰 수정
    public ReviewResponseDTO update(Integer reviewId, ReviewRequestDTO request) {
        System.out.println("[RestAreaService] update reviewId : "+ reviewId);
        System.out.println("[RestAreaService] update : "+ request);

        ReviewEntity entity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다. ID = " + reviewId));
        
        entity.setRating(request.getRating());
        entity.setComment(request.getComment());

        return ReviewResponseDTO.fromEntity(reviewRepository.save(entity)) ;

    }
   
    // 리뷰 삭제
    public Void delete(Integer reviewId) {
        
        reviewRepository.deleteById(reviewId);
        
        return null;
    }
}
