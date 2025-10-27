package com.mini.mini_2.member.domain;

import com.mini.mini_2.member.domain.vo.Email;
import com.mini.mini_2.member.domain.vo.Nickname;
import com.mini.mini_2.member.domain.vo.Password;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true, length = 100))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password", nullable = false, length = 100))
    private Password password;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "nickname", nullable = false, unique = true, length = 50))
    private Nickname nickname;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Favorite> favorites = new ArrayList<>();

    public void updateProfile(Nickname newNickname, Password newEncodedPassword) {
        this.nickname = newNickname;
        this.password = newEncodedPassword;
    }

    public void addFavorite(Favorite favorite) {
        this.favorites.add(favorite);
    }

    public void removeFavorite(Long favoriteId) {
        this.favorites.removeIf(f -> f.getId().equals(favoriteId));
    }
}
