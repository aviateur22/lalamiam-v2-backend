package com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.entity.impl;

import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalAccountInformation;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalInformation;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.IProfessionalInscriptionConfirmationInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.entity.IProfessionalInscriptionConfirmation;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.provider.IProfessionalInscriptionConfirmationRepository;

import java.time.ZonedDateTime;

public class ProfessionalInscriptionConfirmationImpl implements IProfessionalInscriptionConfirmation {

  private final IProfessionalInscriptionConfirmationInput professionalInscriptionConfirmationInput;
  private final IProfessionalInscriptionConfirmationRepository professionalInscriptionConfirmationRepository;
  private final IMessageService messageService;
  private IProfessionalInformation professioanlInformation;
  private IProfessionalAccountInformation professionalAccountInformation;

  public ProfessionalInscriptionConfirmationImpl(
          IProfessionalInscriptionConfirmationInput professionalInscriptionConfirmationInput,
          IProfessionalInscriptionConfirmationRepository professionalInscriptionConfirmationRepository,
          IMessageService messageService) {
    this.professionalInscriptionConfirmationInput = professionalInscriptionConfirmationInput;
    this.professionalInscriptionConfirmationRepository = professionalInscriptionConfirmationRepository;
    this.messageService = messageService;
  }

  @Override
  public IProfessionalInscriptionConfirmation findProfessionalEmail() {
    IProfessionalInformation professionalInformation = professionalInscriptionConfirmationRepository
            .findProfessionalByEmail(professionalInscriptionConfirmationInput.getProfessionalEmail());

    if(professionalInformation == null) {
      throw new BadRequestException(messageService.getMessage("professional.account.confirmed.error"));
    }
    // Information sur le professionel
    this.professioanlInformation = professionalInformation;

    // Information sur le compte du professionel
    this.professionalAccountInformation = professionalInformation.getProfessionalAccountInformation();

    return this;
  }

  @Override
  public IProfessionalInscriptionConfirmation isRegisterConfirmedByProfessional() {

    if(this.professionalAccountInformation.getIsRegisterConfirmByProfessional()) {
      throw new BadRequestException(messageService.getMessage("professional.account.already.confirmed"));
    }

    return this;
  }

  @Override
  public IProfessionalInscriptionConfirmation isProfessionalAccountActive() {

    if(this.professionalAccountInformation.getIsAccountActivated()) {
      throw new BadRequestException(messageService.getMessage("professional.account.already.activated"));
    }

    return this;
  }

  @Override
  public IProfessionalInscriptionConfirmation isConfirmationDateValid(ZonedDateTime actualDate) {
    boolean isConfirmationDateValid = actualDate.isBefore(this.professionalAccountInformation.getAccountRegisterConfirmationLimitTime());

    if(!isConfirmationDateValid) {
      throw new BadRequestException(messageService.getMessage("professional.account.limit.date.expired"));
    }

    return this;
  }

  @Override
  public void persistProfessionalInscriptionConfirmation() {
    professionalInscriptionConfirmationRepository.confirmProfessionalRegister(professioanlInformation.getProfessionalId());
  }
}
