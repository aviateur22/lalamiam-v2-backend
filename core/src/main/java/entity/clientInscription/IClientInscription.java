package entity.clientInscription;

/**
 * Gestion de l'inscription client
 */
public interface IClientInscription {

  public interface ICreatedAccount {
    public Long getAccountId();
  }

  public interface ICreatedClient {
    public Long getClientId();
  }

  public interface ICreatedRole {
    public Long getClientRole();
    public Integer getRoleId();
  }
}
