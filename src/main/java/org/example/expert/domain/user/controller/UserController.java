package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangeRequest;
import org.example.expert.domain.user.dto.response.UserNicknameResponse;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    // 여기에 닉네임 변경 기능 추가?
    @PutMapping("/users")
    public void changePassword(@AuthenticationPrincipal AuthUser authUser, @RequestBody UserChangeRequest userChangeRequest) {
        userService.changePassword(authUser.getId(), userChangeRequest);
    }

    // 로그인된 유저 닉네임을 가져오는 url
    @GetMapping("/users/nickname")
    public ResponseEntity<UserNicknameResponse> getUserNickName(@AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.ok(userService.getUserNickName(authUser.getId()));
    }
}
