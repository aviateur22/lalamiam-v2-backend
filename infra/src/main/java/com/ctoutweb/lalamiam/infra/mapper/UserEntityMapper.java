package com.ctoutweb.lalamiam.infra.mapper;

import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipal;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {
  public UserEntity map(UserPrincipal userDetails) {
    UserEntity user = new UserEntity();
    user.setId(userDetails.id());
    user.setEmail(userDetails.getUsername());
    return user;
  }
}
