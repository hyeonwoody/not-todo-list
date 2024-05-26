package com.toyproject.notTodoList.domain.auth.presentation.controller;

import com.toyproject.notTodoList.domain.auth.application.AuthService;
import com.toyproject.notTodoList.domain.auth.presentation.dto.req.OAuthAPIRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "OAuth Controller")
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final AuthService authService;

    @GetMapping("/success")
    public ResponseEntity<String> googleLogin(@RequestParam("accessToken") String accessToken){
        System.out.println(accessToken);
        return ResponseEntity.ok("AAA");
    }
}
