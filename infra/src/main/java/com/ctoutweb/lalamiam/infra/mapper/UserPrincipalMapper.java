package com.ctoutweb.lalamiam.infra.mapper;

import com.ctoutweb.lalamiam.infra.repository.entity.RoleUserEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserPrincipalMapper {

  /**
   * Map un userEntity en UserPrincipal
   * @param user UserEntity user - Personne que se connecte
   * @return UserPrincipal
   */
  public UserPrincipal map(UserEntity user) {
    return new UserPrincipal(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            this.convertRoleUserToAuthorities(user.getUserRoles()),
            user.getUserAccountInformation().getIsAccountActive()
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
