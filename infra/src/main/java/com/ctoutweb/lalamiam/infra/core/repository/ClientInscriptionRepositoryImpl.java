package com.ctoutweb.lalamiam.infra.core.repository;

import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.IClientInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedAccount;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedClient;
import com.ctoutweb.lalamiam.infra.core.factory.CoreFactory;
import com.ctoutweb.lalamiam.infra.repository.IRoleUserRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserAccountRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.RoleEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.RoleUserEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ClientInscriptionRepositoryImpl implements IClientInscriptionRepository  {
  private final IUserRepository userRepository;
  private final IUserAccountRepository userAccountRepository;
  private final IRoleUserRepository roleUserRepository;
  private final CoreFactory factory;

  public ClientInscriptionRepositoryImpl(
          IUserRepository userRepository,
          IUserAccountRepository userAccountRepository,
          IRoleUserRepository roleUserRepository,
          CoreFactory factory
  ) {
    this.userRepository = userRepository;
    this.userAccountRepository = userAccountRepository;
    this.roleUserRepository = roleUserRepository;
    this.factory = factory;
  }

  @Override
  public Boolean isEmailAvailable(String email) {
    UserEntity findUserByMail = userRepository.findByEmail(email).orElse(null);

    if(findUserByMail == null)
      return true;

    return false;
  }

  @Override
  public ICreatedClient addClient(String email, String hashPassword, String userName) {
    UserEntity user = new UserEntity();

    user.setEmail(email);
    user.setPassword(hashPassword);
    user.setUsrerName(userName);

    UserEntity addUser = userRepository.save(user);

    // Création des rols utilisateurs
    Long roleId = createRoleClient(addUser.getId());

    return factory.getImlpl(addUser.getId(), roleId);
  }

  private Long createRoleClient(long clientId) {

    RoleUserEntity roleUser = new RoleUserEntity();

    RoleEntity role = new RoleEntity();
    role.setId(1);

    UserEntity user = new UserEntity();
    user.setId(clientId);

    roleUser.setUser(user);
    roleUser.setRole(role);

    RoleUserEntity addRoleUser = roleUserRepository.save(roleUser);

    return addRoleUser.getId();
  }

  @Override
  public ICreatedAccount createAccountClient(long clientId) {
    UserAccountEntity userAccount = new UserAccountEntity();

    UserEntity user = new UserEntity();
    user.setId(clientId);
    userAccount.setUser(user);
    userAccount.setIsAccountActive(false);

    UserAccountEntity saveAccount = userAccountRepository.save(userAccount);

    return factory.getImpl(userAccount);
  }
}
