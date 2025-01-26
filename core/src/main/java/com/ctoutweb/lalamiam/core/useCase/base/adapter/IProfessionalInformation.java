package com.ctoutweb.lalamiam.core.useCase.base.adapter;

import java.time.ZonedDateTime;

public interface IProfessionalInformation {
  Long getProfessionalId();
  String getEmail();
  IProfessionalAccountInformation getProfessionalAccountInformation();

}
