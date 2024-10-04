package org.example.expert.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserNicknameResponse {
    private final String nickname;

    public UserNicknameResponse(String nickname) {
        this.nickname = nickname;
    }
}
