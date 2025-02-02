package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;
import com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional.ProfessionalToActivateDetailDto;
import com.ctoutweb.lalamiam.infra.utility.DateUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ProfessionalToActivateDetailMapper {
  @Value("${zone.id}")
  private String zoneId;
  public ProfessionalToActivateDetailDto map(IProfessionalDetail professionalDetail) {
    return new ProfessionalToActivateDetailDto(
            professionalDetail.getProfessionalId(),
            professionalDetail.getProfessionalEmail(),
            professionalDetail.getPhoneNumber(),
            professionalDetail.getFirstName(),
            professionalDetail.getLastName(),
            professionalDetail.getStatutDocuments(),
            DateUtility.uctToZonedDateTime(ZoneId.of(zoneId), professionalDetail.getAccountCreatedAt()),
            DateUtility.uctToZonedDateTime(ZoneId.of(zoneId), professionalDetail.getAccountRegisterConfirmAt())
    );
  }

}
