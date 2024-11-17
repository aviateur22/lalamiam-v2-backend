package entity.clientInscription.impl.entity;
;
import entity.clientInscription.IClientInscription.ICreatedAccount;

public record CreatedAccountImpl(Long accountId) implements ICreatedAccount {
  @Override
  public Long getAccountId() {
    return accountId;
  }
}
