package com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway;


public interface IClientInscriptionRepository {
  /**
   * Recherche client par email
   * @param email String - email du client
   * @return Optional<CreatedClientAdapter>
   */
  public Boolean isEmailAvailable(String email);

  /**
   * Ajout d'un nouveau client
   * @param email String
   * @param hashPassword String
   * @return CreatedClientAdapter
   */
  public ICreatedClient addClient(String email, String hashPassword, String nickame);

  /**
   * Creation du compte client
   * @param clientId Long
   * @return CreatedAccountAdapter
   */
  public ICreatedAccount createAccountClient(long clientId);
}
