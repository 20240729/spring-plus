package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.request.UserChangeRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        return new UserResponse(user.getId(), user.getEmail());
    }

    @Transactional
    public void changePassword(long userId, UserChangeRequest userChangeRequest) {
        validateNewPassword(userChangeRequest);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("User not found"));

        if (!passwordEncoder.matches(userChangeRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidRequestException("잘못된 비밀번호입니다.");
        }

        // 새 비밀번호가 없지 않을 경우에만 비밀번호를 바꿈
        if(!userChangeRequest.getNewPassword().isBlank()){
            if (passwordEncoder.matches(userChangeRequest.getNewPassword(), user.getPassword())) {
                throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
            }
            user.changePassword(passwordEncoder.encode(userChangeRequest.getNewPassword()));
        }

        // 새 닉네임이 있으면 닉네임을 바꿈
        if(!userChangeRequest.getNickName().isBlank()){
            user.updateNickName(userChangeRequest.getNickName());
        }

    }

    private static void validateNewPassword(UserChangeRequest userChangeRequest) {
        if (userChangeRequest.getNewPassword().length() < 8 ||
                !userChangeRequest.getNewPassword().matches(".*\\d.*") ||
                !userChangeRequest.getNewPassword().matches(".*[A-Z].*")) {
            throw new InvalidRequestException("새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.");
        }
    }
}
