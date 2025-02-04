package com.ctoutweb.lalamiam.core.useCase.base.gateway;

public interface IProfessionalInformation {
  Long getProfessionalId();
  String getEmail();
  IProfessionalAccountInformation getProfessionalAccountInformation();

}
