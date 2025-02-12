package com.ctoutweb.lalamiam.infra.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDto(
        @NotNull(message = "{email.missing}")
        @NotBlank(message = "{email.missing}")
        @Email(message = "{email.bad.format}")
        String email,
        @NotNull(message = "{password.missing}")
        @NotBlank(message = "{password.missing}")
        String password) {
}
