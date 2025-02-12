package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.dto.client.ClientRegisterAsProfessionalDto;
import com.ctoutweb.lalamiam.infra.model.IMessageResponse;

public interface IClientService {

  /**
   * Client s'inscrit en tant que Professionnel
   * @param dto ClientRegisterAsProfessionalDto
   * @return IMessageResponse
   */
  public IMessageResponse clientRegisterAsProfessional(ClientRegisterAsProfessionalDto dto);
}
