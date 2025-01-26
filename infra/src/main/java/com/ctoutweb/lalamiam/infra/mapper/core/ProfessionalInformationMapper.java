package com.ctoutweb.lalamiam.infra.mapper.core;

import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalAccountInformation;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalInformation;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import org.springframework.stereotype.Component;

/**
 * Map vers un IProfessionalInformation
 */
@Component
public class ProfessionalInformationMapper {

  private final Factory factory;

  public ProfessionalInformationMapper(Factory factory) {
    this.factory = factory;
  }

  public IProfessionalInformation map(
          Long professionalId,
          String email,
          IProfessionalAccountInformation professionalAccountInformation) {
    return factory.getProfessionalInformationImpl(professionalId, email, professionalAccountInformation);
  }
}
