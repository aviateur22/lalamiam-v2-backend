package com.ctoutweb.lalamiam.core.useCase.clientInscription.port;

import com.ctoutweb.lalamiam.core.adapter.IResponse;

public interface IClientInscriptionOutput extends IResponse {
  Long getUserId();
  Long getUserAccountId();
}
