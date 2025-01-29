package com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter;

public interface ILostPasswordRepository {
  public boolean updateLostPassword(String email, String password);
}
