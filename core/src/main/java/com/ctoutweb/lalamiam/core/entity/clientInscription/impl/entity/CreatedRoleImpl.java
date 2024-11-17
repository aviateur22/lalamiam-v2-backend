package com.ctoutweb.lalamiam.core.entity.clientInscription.impl.entity;

import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription;

public record CreatedRoleImpl(long accountId) implements IClientInscription.ICreatedRole {
  @Override
  public Long getClientRole() {
    return null;
  }

  @Override
  public Integer getRoleId() {
    return null;
  }
}
