package com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional;

import java.time.ZonedDateTime;

public record ProfessionalToActivateResumeDto(Long id,
                                              String professionalEmail,
                                              ZonedDateTime accountCreatedAt) {
}
