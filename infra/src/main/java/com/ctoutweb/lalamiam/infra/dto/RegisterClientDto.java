package com.ctoutweb.lalamiam.infra.dto;

import com.ctoutweb.lalamiam.infra.annotation.custom.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Données pour l'inscription client
 */
public record RegisterClientDto(

        @NotNull(message = "{email.missing}")
        @NotBlank(message = "{email.missing}")
        @Email(message = "{email.bad.format}")
        String email,

        @PasswordConstraint(message = "{password.bad.format}")
        String password,

        @NotNull(message = "{password.missing}")
        @NotBlank(message = "{password.missing}")
        String userName) {

}
