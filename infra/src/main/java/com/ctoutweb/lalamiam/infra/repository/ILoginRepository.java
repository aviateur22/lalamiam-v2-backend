package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.repository.entity.LoginEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILoginRepository extends JpaRepository<LoginEntity, Long> {
  /**
   * Récupération de toutes les connexions d'un client
   * @param user UserEntity
   * @return List<UserLoginEntity>
   */
  List<LoginEntity> findByUserOrderByLoginAtDesc(UserEntity user);
}
