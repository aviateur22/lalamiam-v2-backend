package provider;

import entity.common.ITokenToValidate;

public interface IValidateTokenService {
  public boolean validateHashToken(ITokenToValidate tokenWithHashToken);
}
