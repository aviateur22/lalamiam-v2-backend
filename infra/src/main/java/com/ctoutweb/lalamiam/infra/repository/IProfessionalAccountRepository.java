package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProfessionalAccountRepository extends JpaRepository<ProfessionalAccountEntity, Long> {

  /**
   * Recherche un compte par un utilisateur
   * @param user UserEntity
   * @return Optional<ProfessionalAccountEntity>
   */
  public Optional<ProfessionalAccountEntity> findByUser(UserEntity user);
}
