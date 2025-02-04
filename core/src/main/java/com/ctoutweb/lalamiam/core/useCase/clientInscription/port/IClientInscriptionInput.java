package com.ctoutweb.lalamiam.core.useCase.clientInscription.port;

public interface IClientInscriptionInput {
  String getHashPassword();
  String getEmail();
  String getNickName();
}
