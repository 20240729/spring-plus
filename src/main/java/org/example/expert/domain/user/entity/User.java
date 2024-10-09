package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.user.enums.UserRole;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String nickName;

    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @Builder
    private User(Long id, String email, UserRole userRole, String nickName) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
        this.nickName = nickName;
    }

    public static User fromAuthUser(AuthUser authUser) {

        UserRole role = UserRole.valueOf(authUser.getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("권한이 없습니다."))
                .getAuthority());

        return new User(
                authUser.getId(),
                authUser.getEmail(),
                role,
                authUser.getNickName()
        );
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }

    // 닉네임 업데이트 메서드 적기
    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }
}
