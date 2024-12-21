package com.ctoutweb.lalamiam.infra.boundaries;

import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.model.auth.RegisterBoundaryInputImpl;
import org.springframework.stereotype.Component;

@Component
public record ClientInscriptionBoundaryInput() {

  public IBoundaryInputAdapter map(RegisterClientDto dto, String hashPassword) {
    String email = dto.email();
    String userName = dto.userName();
    return new RegisterBoundaryInputImpl(
            hashPassword,
            email,
            userName
    );
  }
}
