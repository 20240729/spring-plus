package org.example.expert.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangeRequest {

    @NotBlank
    private String oldPassword;
    private String newPassword;
    private String nickName;
    // 새 패스워드 빈 칸 가능으로 함
}
