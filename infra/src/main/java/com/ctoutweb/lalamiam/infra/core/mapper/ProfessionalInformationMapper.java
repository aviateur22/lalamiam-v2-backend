package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalAccountInformation;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalInformation;
import com.ctoutweb.lalamiam.infra.core.factory.CoreFactory;
import org.springframework.stereotype.Component;

/**
 * Map vers un IProfessionalInformation
 */
@Component
public class ProfessionalInformationMapper {

  private final CoreFactory factory;

  public ProfessionalInformationMapper(CoreFactory factory) {
    this.factory = factory;
  }

  public IProfessionalInformation map(
          Long professionalId,
          String email,
          IProfessionalAccountInformation professionalAccountInformation) {
    return factory.getProfessionalInformationImpl(professionalId, email, professionalAccountInformation);
  }
}
