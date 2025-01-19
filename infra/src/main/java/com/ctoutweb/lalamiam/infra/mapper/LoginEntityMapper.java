package com.ctoutweb.lalamiam.infra.mapper;

import com.ctoutweb.lalamiam.infra.factory.EntityFactory;
import com.ctoutweb.lalamiam.infra.repository.entity.LoginEntity;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class LoginEntityMapper {
  private final EntityFactory entityFactory;

  public LoginEntityMapper(EntityFactory entityFactory) {
    this.entityFactory = entityFactory;
  }

  public LoginEntity map(Long userId, ZonedDateTime loginTime, boolean isAuthenticationValid) {
    final  boolean HAS_TO_BE_ChECK = true;
    return entityFactory.getLoginEntityFrom(userId, loginTime, isAuthenticationValid, HAS_TO_BE_ChECK);
  }
}
