package com.toyproject.notTodoList.domain.auth.presentation.controller;

import com.toyproject.notTodoList.domain.auth.application.AuthService;
import com.toyproject.notTodoList.domain.auth.application.dto.req.RegisterRequest;
import com.toyproject.notTodoList.domain.auth.application.dto.res.RegisterResponse;
import com.toyproject.notTodoList.domain.auth.presentation.dto.req.RegisterAPIRequest;
import com.toyproject.notTodoList.domain.auth.presentation.dto.res.RegisterAPIResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/exists/{username}")
    public ResponseEntity<String> checkDuplication (@PathVariable @NotEmpty String username) {
        authService.isUsernameTaken(username);
        return ResponseEntity.ok("사용 가능한 아이디 입니다.");
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterAPIResponse> register (@RequestBody @Valid RegisterAPIRequest request){
        RegisterResponse response = authService.register(RegisterRequest.from(request));
        return ResponseEntity.ok(RegisterAPIResponse.from(response));
    }
}
