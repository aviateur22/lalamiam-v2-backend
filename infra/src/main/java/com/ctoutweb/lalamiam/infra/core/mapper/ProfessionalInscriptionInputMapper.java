package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;
import com.ctoutweb.lalamiam.infra.core.model.ProfessionalRegisterInputImpl;
import org.springframework.stereotype.Component;

@Component
public record ProfessionalInscriptionInputMapper() {
  public IProfessionalInscriptionInput map(RegisterProfessionalDto dto, String hashPassword) {
    return new ProfessionalRegisterInputImpl(dto, hashPassword);
  }
}
