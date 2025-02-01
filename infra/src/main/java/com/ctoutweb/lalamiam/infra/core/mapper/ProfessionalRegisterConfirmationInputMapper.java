package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.IProfessionalInscriptionConfirmationInput;
import com.ctoutweb.lalamiam.infra.core.factory.CoreFactory;
import com.ctoutweb.lalamiam.infra.dto.RegisterConfirmByProfessionalDto;
import org.springframework.stereotype.Component;

/**
 * Map vers un IProfessionalInscriptionConfirmationInput
 */
@Component
public class ProfessionalRegisterConfirmationInputMapper {
  private final CoreFactory factory;

  public ProfessionalRegisterConfirmationInputMapper(CoreFactory factory) {
    this.factory = factory;
  }

  public IProfessionalInscriptionConfirmationInput map(RegisterConfirmByProfessionalDto dto) {
    return factory.getProfessionalInscriptionConfirmationInputImpl(dto);
  }
}
