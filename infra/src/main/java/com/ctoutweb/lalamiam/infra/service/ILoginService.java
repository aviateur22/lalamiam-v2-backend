package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.model.IUserLoginStatus;
import com.ctoutweb.lalamiam.infra.repository.entity.LoginEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;

import java.util.List;

public interface ILoginService {

  /**
   * Mise a jour des information de connexion d'un utilisateur
   * @param user - UserEntity - Utilisateur voulant se connecter
   * @param isAuthenticationValid boolean - Validité de l'authentication
   * @return List<LoginEntity>
   */
  public List<LoginEntity> updateUserLoginInformation(UserEntity user, boolean isAuthenticationValid);


  /**
   * Ajout d'un délai de blocage de connexion dans le connexion utilisateur
   * @param user UserEntity - Personne souhaitant se connnecter
   * @param lastUserLoginList - List des dernieres connexion du client
   */
  public void addDelayOnLogin(UserEntity user, List<LoginEntity> lastUserLoginList);

  /**
   * Vérification si une connexion peut avoir lieu
   * @param userId Long - Utilisateur voulant se connecter
   * @return IUserLoginStatus
   */
  public IUserLoginStatus isLoginAuthorize(Long userId);

  /**
   * Récupération du nombre de connexion disponible
   * @param lastUserLoginList List<UserLoginEntity> - Liste des dernieres connexions d'un client
   * @return Integer
   */
  public Integer getUserRemainingLogin(List<LoginEntity> lastUserLoginList);

  /**
   * Suppression de la prise en compte des dernieres connexion d'un utilisateur
   * @param lastUserLoginList List<UserLoginEntity>
   */
  public void resetUserConnexionStatus(List<LoginEntity> lastUserLoginList);
}
