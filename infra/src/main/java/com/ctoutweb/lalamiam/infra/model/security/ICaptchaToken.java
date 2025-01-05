package com.ctoutweb.lalamiam.infra.model.security;

public interface ICaptchaToken {
  ICaptchaToken getCryptographicType();
  Boolean isCaptchaResponseValid();
}
