package com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Renvoie les informations sur un professionel à activer
 *
 */
public record ProfessionalToActivateDetailDto(
        Long professionalId,
        String professionalEmail,
        String phoneNumber,
        String firstName,
        String lastName,
        List<String> files,
        ZonedDateTime accountCreatedAt,
        ZonedDateTime accountRegisterConfirmAt
) {}
