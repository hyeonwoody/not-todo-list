package com.toyproject.notTodoList.domain.auth.presentation.controller;

import com.toyproject.notTodoList.domain.auth.application.AuthService;
import com.toyproject.notTodoList.domain.auth.application.dto.req.RegisterRequest;
import com.toyproject.notTodoList.domain.auth.application.dto.res.LoginResponse;
import com.toyproject.notTodoList.domain.auth.application.dto.res.RegisterResponse;

import com.toyproject.notTodoList.domain.auth.jwt.JwtAuthenticationContext;
import com.toyproject.notTodoList.domain.auth.jwt.JwtAuthenticationProvider;
import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import com.toyproject.notTodoList.domain.auth.presentation.dto.req.LoginAPIRequest;
import com.toyproject.notTodoList.domain.auth.presentation.dto.req.RegisterAPIRequest;
import com.toyproject.notTodoList.domain.auth.presentation.dto.res.DetailLoginAPIResponse;
import com.toyproject.notTodoList.domain.auth.presentation.dto.res.RegisterAPIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Auth API")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @Operation(summary = "Check username availability", description = "Checks if the provided username is available for registration.")
    @GetMapping("/exists/{username}")
    public ResponseEntity<String> checkDuplication (@PathVariable @NotEmpty String username) {
        authService.isUsernameTaken(username);
        return ResponseEntity.ok("사용 가능한 아이디 입니다.");
    }

    @Operation(summary = "Register Member", description = "Registers a new member with the provided information. Username availability is checked before registration.")
    @PostMapping("/register")
    public ResponseEntity<RegisterAPIResponse> register (@RequestBody @Valid RegisterAPIRequest request){
        RegisterResponse response = authService.register(RegisterRequest.from(request));
        return ResponseEntity.ok(RegisterAPIResponse.from(response));
    }

    @Operation(
            summary = "Login Member",
            description = "Attempts to authenticate a member with the provided username and password. If successful, a JWT token and login details will be returned."
    )
    @PostMapping("/login")
    public ResponseEntity<DetailLoginAPIResponse> login (@RequestBody @Valid LoginAPIRequest request){
        JwtAuthenticationContext authToken = new JwtAuthenticationContext(request.getUsername(), request.getPassword());
        Authentication token = authenticationManager.authenticate(authToken);
        JwtToken authentication = (JwtToken) token.getPrincipal();
        LoginResponse response = (LoginResponse) token.getDetails();
        return ResponseEntity.ok(DetailLoginAPIResponse.of(response, authentication));
    }
}
