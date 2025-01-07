package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.repository.entity.DelayLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDelayLoginRepository extends JpaRepository<DelayLoginEntity, Long> {
  /**
   * Recherche d'un delai de connexion
   * @param id - Long - Id utilisateur se connectant
   * @return Optional<DelayLoginEntity>
   */
  public Optional<DelayLoginEntity> findFirstByUserId(Long id);
}
