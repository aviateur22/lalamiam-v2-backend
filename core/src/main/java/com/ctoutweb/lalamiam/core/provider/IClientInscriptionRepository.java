package com.ctoutweb.lalamiam.core.provider;

import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedRole;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedClient;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedAccount;

import java.util.Optional;

public interface IClientInscriptionRepository {
  /**
   * Recherche client par email
   * @param email String - email du client
   * @return Optional<CreatedClientAdapter>
   */
  public Optional<ICreatedClient> findUserByEmail(String email);

  /**
   * Ajout d'un nouveau client
   * @param email String
   * @param hashPassword String
   * @return CreatedClientAdapter
   */
  public ICreatedClient addClient(String email, String hashPassword);

  /**
   * Création des roles clients
   * @param clientId Long
   * @param clientRoleId Integer
   */
  public ICreatedRole createRoleClient(long clientId, int clientRoleId);

  /**
   * Creation du compte client
   * @param clientId Long
   * @return CreatedAccountAdapter
   */
  public ICreatedAccount createAccountClient(long clientId);
}
