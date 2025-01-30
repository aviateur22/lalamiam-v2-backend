package com.ctoutweb.lalamiam.infra.dto;

import com.ctoutweb.lalamiam.infra.annotation.custom.PasswordConstraint;

public record ReinitializeLostPasswordDto(
        String email,
        String urlToken,
        @PasswordConstraint(message = "{password.bad.format}")
        String password) {
}
