package com.ctoutweb.lalamiam.infra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterConfirmByProfessionalDto(
        @NotNull(message = "{register.confirmation.error}")
        @NotBlank(message = "{register.confirmation.error}")
        String email,

        @NotNull(message = "{register.confirmation.error}")
        @NotBlank(message = "{register.confirmation.error}")
        String urlToken,

        @NotNull(message = "{register.confirmation.email.token.missing}")
        @NotBlank(message = "{register.confirmation.email.token.missing}")
        String emailToken) {
}
