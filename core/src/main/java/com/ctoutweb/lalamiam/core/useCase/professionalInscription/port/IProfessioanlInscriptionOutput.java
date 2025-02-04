package com.ctoutweb.lalamiam.core.useCase.professionalInscription.port;

import com.ctoutweb.lalamiam.core.useCase.base.port.IResponse;

import java.util.List;

public interface IProfessioanlInscriptionOutput extends IResponse {
  Long getProfessionalId();
  Long getProfessionalAccountId();
  List<Long> getInscriptionDocumentsIds();
}
