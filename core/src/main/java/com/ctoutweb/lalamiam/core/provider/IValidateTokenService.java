package com.ctoutweb.lalamiam.core.provider;

import com.ctoutweb.lalamiam.core.entity.common.ITokenToValidate;

public interface IValidateTokenService {
  public boolean validateHashToken(ITokenToValidate tokenWithHashToken);
}
