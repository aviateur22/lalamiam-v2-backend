package com.ctoutweb.lalamiam.infra.model.auth;

public interface IProfessionalRegisterToken extends IUserRegisterToken {
  public String getPlainTextEmailToken();
}
