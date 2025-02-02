package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivate;
import com.ctoutweb.lalamiam.infra.core.factory.CoreFactory;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.utility.DateUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ProfessionalToActivateMapper {
  private final CoreFactory factory;

  @Value("${zone.id}")
  private String zoneId;

  public ProfessionalToActivateMapper(CoreFactory factory) {
    this.factory = factory;
  }

  public IProfessionalToActivate map(ProfessionalAccountEntity professionalAccount) {
    UserEntity professionalInfo = professionalAccount.getUser();
    IProfessionalToActivate professionalToActivate = factory.getProfessionalToActivateImpl(
            professionalInfo.getEmail(),
            professionalInfo.getId(),
            DateUtility.uctToZonedDateTime(ZoneId.of(zoneId), professionalAccount.getCreatedAt())
    );
    return professionalToActivate;
  }
}
