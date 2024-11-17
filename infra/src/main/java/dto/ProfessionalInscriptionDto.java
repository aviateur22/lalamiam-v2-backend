package dto;

import port.professionalInscription.IProfessionalInscriptionInformation;

public record ProfessionalInscriptionDto() implements IProfessionalInscriptionInformation {
  @Override
  public String getName() {
    return null;
  }

  @Override
  public String getPhone() {
    return null;
  }

  @Override
  public String getEmail() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }
}
