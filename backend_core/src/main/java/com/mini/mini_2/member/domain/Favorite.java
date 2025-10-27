package com.mini.mini_2.member.domain;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 즐겨찾기 엔티티
 */
@Entity
@Table(name = "favorite")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rest_area_id")
    private RestAreaEntity restArea;

    public static Favorite create(Member member, RestAreaEntity restArea, String description) {
        return Favorite.builder()
                .member(member)
                .restArea(restArea)
                .description(description)
                .build();
    }
}
