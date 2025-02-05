package com.ctoutweb.lalamiam.infra.model.impl;


import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedAccount;
import com.ctoutweb.lalamiam.infra.repository.entity.UserAccountEntity;

public record CreatedAccountImpl(UserAccountEntity userAccount) implements ICreatedAccount {
  @Override
  public Long getAccountId() {
    return userAccount.getId();
  }
}
