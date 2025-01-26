package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITokenRepository extends JpaRepository<TokenEntity, Long> {
  TokenEntity findFirstByUserAndCryptographyType(UserEntity user, String cryptographyType);

  Long deleteByUserAndCryptographyType(UserEntity user, String cryptographyType);
}
