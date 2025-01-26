package com.ctoutweb.lalamiam.infra.mapper.core;

import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.IProfessionalInscriptionConfirmationInput;
import com.ctoutweb.lalamiam.infra.dto.RegisterConfirmByProfessionalDto;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.core.adapter.ProfessionalRegisterConfirmationInputBoundaryImpl;
import org.springframework.stereotype.Component;

/**
 * Map vers un IProfessionalInscriptionConfirmationInput
 */
@Component
public class ProfessionalRegisterConfirmationInputMapper {
  private final Factory factory;

  public ProfessionalRegisterConfirmationInputMapper(Factory factory) {
    this.factory = factory;
  }

  public IProfessionalInscriptionConfirmationInput map(RegisterConfirmByProfessionalDto dto) {
    return factory.getProfessionalInscriptionConfirmationInputImpl(dto);
  }
}
