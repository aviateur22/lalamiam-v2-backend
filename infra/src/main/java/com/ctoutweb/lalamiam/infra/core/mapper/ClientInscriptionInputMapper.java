package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;
import com.ctoutweb.lalamiam.infra.dto.auth.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.core.model.ClientRegisterBoundaryInputImpl;
import org.springframework.stereotype.Component;

@Component
public record ClientInscriptionInputMapper() {

  public IClientInscriptionInput map(RegisterClientDto dto, String hashPassword) {
    String email = dto.email();
    String nickName = dto.nickname();
    return new ClientRegisterBoundaryInputImpl(
            hashPassword,
            email,
            nickName
    );
  }
}
