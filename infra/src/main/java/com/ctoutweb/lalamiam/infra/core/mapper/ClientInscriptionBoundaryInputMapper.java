package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.core.model.ClientRegisterBoundaryInputImpl;
import org.springframework.stereotype.Component;

@Component
public record ClientInscriptionBoundaryInputMapper() {

  public IBoundaryInputAdapter map(RegisterClientDto dto, String hashPassword) {
    String email = dto.email();
    String nickName = dto.nickname();
    return new ClientRegisterBoundaryInputImpl(
            hashPassword,
            email,
            nickName
    );
  }
}
