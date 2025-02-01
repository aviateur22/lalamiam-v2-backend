package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;
import com.ctoutweb.lalamiam.infra.core.model.ProfessionalRegisterBoundaryInputImpl;
import org.springframework.stereotype.Component;

@Component
public record ProfessionalInscriptionBoundaryInputMapper() {
  public IBoundaryInputAdapter map(RegisterProfessionalDto dto, String hashPassword) {
    return new ProfessionalRegisterBoundaryInputImpl(dto, hashPassword);
  }
}
