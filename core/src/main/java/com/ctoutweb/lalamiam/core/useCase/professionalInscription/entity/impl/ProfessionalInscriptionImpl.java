package com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.impl;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.impl.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.IProfessioanlInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.IProfessionalInscription;

public class ProfessionalInscriptionImpl implements IProfessionalInscription {
  private IProfessionalInscriptionInput professionalInscriptionInformation;
  private final ClientInscriptionUseCase clientInscriptionUseCase;
  private final IMessageService messageService;

  public ProfessionalInscriptionImpl(
          IProfessionalInscriptionInput professionalInscriptionInformation,
          ClientInscriptionUseCase clientInscriptionUseCase,
          IMessageService messageService) {
    this.professionalInscriptionInformation = professionalInscriptionInformation;
    this.clientInscriptionUseCase = clientInscriptionUseCase;
    this.messageService = messageService;
  }

  @Override
  public IProfessionalInscription isProfessionalEmailAvailable() {
    return null;
  }

  @Override
  public IProfessionalInscription createUserAccount() {
    return null;
  }

  @Override
  public IProfessionalInscription registerProfessional() {
    return null;
  }

  @Override
  public IProfessionalInscription saveProfessionalInscriptionDocument() {
    return null;
  }

  @Override
  public IProfessionalInscription createProfessionalAccount() {
    return null;
  }

  @Override
  public IProfessioanlInscriptionOutput getProfessionalInscriptionResponse() {
    return null;
  }
}
