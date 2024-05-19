package com.toyproject.notTodoList.domain.auth.presentation.dto.req;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginAPIRequest {
    @NotEmpty
    @Size(max = 12, min =4, message = "Login Id는 4~12자리여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Login ID는 영문자 또는 숫자만 가능합니다.")
    private String username;

    @NotEmpty
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:\",./<>?\\\\|]+$",
            message = "비밀번호는 영문자, 숫자, 특수문자만 허용됩니다.")
    private String password;
}
