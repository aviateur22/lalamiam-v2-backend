package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.repository.entity.JwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJwtRepository extends JpaRepository<JwtEntity, Long> {
  Optional<JwtEntity> findOneByEmail(String email);
  Long deleteByEmail(String email);
}
