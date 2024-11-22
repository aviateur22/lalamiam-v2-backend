package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedRole;
import com.ctoutweb.lalamiam.infra.repository.entity.RoleUserEntity;

public record CreatedRoleImpl(RoleUserEntity roleUser) implements ICreatedRole {
  @Override
  public Long getClientRole() {
    return roleUser.getId();
  }

  @Override
  public Integer getRoleId() {
    return roleUser.getRole().getId();
  }
}
