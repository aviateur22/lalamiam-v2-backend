package com.ctoutweb.lalamiam.infra.dto.admin;

/**
 * Activation d'un professionel
 * @param areProfessionalInformationValid
 * @param professionalId
 */
public record ActivateProfessionalDto(
        Boolean areProfessionalInformationValid,
        Long professionalId

) {
}
