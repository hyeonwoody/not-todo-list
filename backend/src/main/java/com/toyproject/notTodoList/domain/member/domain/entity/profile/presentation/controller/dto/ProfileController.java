package com.toyproject.notTodoList.domain.member.domain.entity.profile.presentation.controller.dto;

import com.toyproject.notTodoList.domain.member.domain.entity.profile.application.ProfileService;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.infrastructure.ProfileJdbcTemplateRepository;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/exists/{nickname}")
    public ResponseEntity<String> checkDuplication (@PathVariable @NotEmpty String nickname) {
        profileService.isNicknameTaken(nickname);
        return ResponseEntity.ok("사용 가능한 닉네임 입니다.");
    }
}
