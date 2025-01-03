package com.ctoutweb.lalamiam.infra.dto;

import com.ctoutweb.lalamiam.infra.annotation.custom.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterProfessionalDto(
        @NotNull(message = "{email.missing}")
        @NotBlank(message = "{email.missing}")
        @Email(message = "{email.bad.format}")
        String email,

        @PasswordConstraint(message = "{password.bad.format}")
        String password,

        @NotNull(message = "{nickname.missing}")
        @NotBlank(message = "{nickname.missing}")
        String nickName,

        @NotNull(message = "{firstname.missing}")
        @NotBlank(message = "{firstname.missing}")
        String firstName,

        @NotNull(message = "{lastname.missing}")
        @NotBlank(message = "{lastname.missing}")
        String lastName,

        @NotNull(message = "{phone.missing}")
        @NotBlank(message = "{phone.missing}")
        String phone,
        @NotNull(message = "{captcha.response.missing}")
        UserCaptchaResponseDto userCaptchaResponse
) {
}
