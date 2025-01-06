package com.ctoutweb.lalamiam.infra.model;

import java.time.ZonedDateTime;

/**
 * Information sur le statut de connexion d'un client
 *
 * - Récupération du droit de connexion
 * - Horaire déblocage de la connexion si bloqué
 *
 */
public interface IUserLoginStatus {
public boolean isLoginAuthorize();
public ZonedDateTime getRecoveryLoginTime();
}
