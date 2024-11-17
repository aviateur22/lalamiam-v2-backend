package entity.clientInscription.impl.entity;

import entity.clientInscription.IClientInscription.ICreatedRole;

public record CreatedRoleImpl(long accountId) implements ICreatedRole {
  @Override
  public Long getClientRole() {
    return null;
  }

  @Override
  public Integer getRoleId() {
    return null;
  }
}
