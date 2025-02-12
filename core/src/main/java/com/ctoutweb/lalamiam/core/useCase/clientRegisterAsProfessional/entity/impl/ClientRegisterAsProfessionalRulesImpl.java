package com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.entity.impl;

import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.entity.IClientRegisterAsProfessionalRules;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.IProfessionalInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessioanlInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;

public class ClientRegisterAsProfessionalRulesImpl implements IClientRegisterAsProfessionalRules {
  private final ICoreMessageService messageService;
  private final ICoreEmailService coreEmailService;
  private final IProfessionalInscriptionRepository professionalInscriptionRepository;

  public ClientRegisterAsProfessionalRulesImpl(
          ICoreMessageService messageService,
          ICoreEmailService coreEmailService,
          IProfessionalInscriptionRepository professionalInscriptionRepository) {
    this.messageService = messageService;
    this.coreEmailService = coreEmailService;
    this.professionalInscriptionRepository = professionalInscriptionRepository;
  }

  @Override
  public IClientRegisterAsProfessionalRules isClientProfessional(IProfessionalInscriptionInput professionalInscriptionInformation) {
    return null;
  }

  @Override
  public IClientRegisterAsProfessionalRules registerProfessional() {
    return null;
  }

  @Override
  public IClientRegisterAsProfessionalRules saveProfessionalInscriptionDocument() {
    return null;
  }

  @Override
  public IClientRegisterAsProfessionalRules createProfessionalAccount() {
    return null;
  }

  @Override
  public IClientRegisterAsProfessionalRules sendRegisterConfirmationEmail() {
    return null;
  }

  @Override
  public IProfessioanlInscriptionOutput getProfessionalInscriptionResponse() {
    return null;
  }
}
