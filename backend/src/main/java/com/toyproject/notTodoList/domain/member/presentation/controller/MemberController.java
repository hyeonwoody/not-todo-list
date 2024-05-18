package com.toyproject.notTodoList.domain.member.presentation.controller;

import com.toyproject.notTodoList.domain.member.application.service.MemberService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/exists/{nickname}")
    public ResponseEntity<String> checkDuplication (@PathVariable @NotEmpty String nickname) {
        memberService.isNicknameTaken(nickname);
        return ResponseEntity.ok("사용 가능한 닉네임 입니다.");
    }
}
