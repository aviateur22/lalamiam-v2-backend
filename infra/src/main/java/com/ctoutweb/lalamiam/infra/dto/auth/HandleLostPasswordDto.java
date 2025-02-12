package com.ctoutweb.lalamiam.infra.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HandleLostPasswordDto(
        @NotNull(message = "{email.missing}")
        @NotBlank(message = "{email.missing}")
        @Email(message = "{email.bad.format}")
        String email
) {
}
