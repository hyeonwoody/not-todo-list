package com.toyproject.notTodoList.domain.auth.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;


@Getter
public class RegisterAPIRequest {

    @NotBlank
    @Size(max = 12, min =4, message = "Login Id는 4~12자리여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Login ID는 영문자 또는 숫자만 가능합니다.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|]+$",
            message = "비밀번호는 영문자, 숫자, 특수문자만 허용됩니다.")
    private String password;

    @NotBlank
    @Size(min = 1, max = 10, message = "별명은 1~10자여야 합니다.")
    private String nickname;
    private Integer authProvider;

}
