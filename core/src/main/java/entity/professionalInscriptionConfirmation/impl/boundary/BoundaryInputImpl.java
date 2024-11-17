package entity.professionalInscriptionConfirmation.impl.boundary;

import adapter.professionalInscriptionConfirmation.IBoundariesAdapter.IBoundaryInputAdapter;

public record BoundaryInputImpl(
        String professionalEmail,
        String professionalEmailToken,
        String professionalUrlToken
) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInputImpl(String professionalEmail, String professionalEmailToken, String professionalUrlToken) {
    return new BoundaryInputImpl(professionalEmail, professionalEmailToken, professionalUrlToken);
  }

  @Override
  public String getProfessionalEmailToken() {
    return professionalEmailToken;
  }

  @Override
  public String getProfessionalUrlToken() {
    return professionalUrlToken;
  }

  @Override
  public String getProfessionalEmail() {
    return professionalEmail;
  }
}
