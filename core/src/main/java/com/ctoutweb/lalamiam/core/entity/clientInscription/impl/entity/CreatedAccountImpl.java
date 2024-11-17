package com.ctoutweb.lalamiam.core.entity.clientInscription.impl.entity;
;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription;

public record CreatedAccountImpl(Long accountId) implements IClientInscription.ICreatedAccount {
  @Override
  public Long getAccountId() {
    return accountId;
  }
}
