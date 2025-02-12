package com.ctoutweb.lalamiam.infra.mapper;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.repository.entity.RoleUserEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserPrincipalMapper {
  private final Factory factory;

  public UserPrincipalMapper(Factory factory) {
    this.factory = factory;
  }

  /**
   * Map un userEntity en UserPrincipal
   * @param user UserEntity user - Personne que se connecte
   * @return UserPrincipal
   */
  public UserPrincipal map(UserEntity user) {
    return factory.getUserPrincipal(
        user.getId(),
        user.getEmail(),
        user.getPassword(),
        user.getUserAccountInformation().getIsAccountActive(),
        this.convertRoleUserToAuthorities(user.getUserRoles())
    );
  }

  /**
   * convertion list<RoleUser> en List<GrantedAuthority>
   * @param roles List<RoleUserEntity>
   * @return List<SimpleGrantedAuthority>
   */
  public List<SimpleGrantedAuthority> convertRoleUserToAuthorities(List<RoleUserEntity> roles) {
    return roles
        .stream()
        .map(role->role.getRole())
        .map(role->new SimpleGrantedAuthority(role.getRole()))
        .collect(Collectors.toList());
  }
}
