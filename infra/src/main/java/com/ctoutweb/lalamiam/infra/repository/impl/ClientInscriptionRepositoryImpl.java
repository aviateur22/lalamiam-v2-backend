package com.ctoutweb.lalamiam.infra.repository.impl;

import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedClient;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedRole;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedAccount;
import com.ctoutweb.lalamiam.core.provider.IClientInscriptionRepository;
import com.ctoutweb.lalamiam.infra.factory.Factory;
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
  private final Factory factory;

  public ClientInscriptionRepositoryImpl(
          IUserRepository userRepository,
          IUserAccountRepository userAccountRepository,
          IRoleUserRepository roleUserRepository,
          Factory factory
  ) {
    this.userRepository = userRepository;
    this.userAccountRepository = userAccountRepository;
    this.roleUserRepository = roleUserRepository;
    this.factory = factory;
  }

  @Override
  public ICreatedClient findUserByEmail(String email) {
    UserEntity findUserByMail = userRepository.findByEmail(email).orElse(null);

    if(findUserByMail == null)
      return null;

    return factory.getImlpl(findUserByMail);
  }

  @Override
  public ICreatedClient addClient(String email, String hashPassword) {
    UserEntity user = new UserEntity();

    user.setEmail(email);
    user.setPassword(hashPassword);

    UserEntity addUser = userRepository.save(user);

    return factory.getImlpl(addUser);
  }

  @Override
  public ICreatedRole createRoleClient(long clientId, int clientRoleId) {

    RoleUserEntity roleUser = new RoleUserEntity();

    RoleEntity role = new RoleEntity();
    role.setId(1);

    UserEntity user = new UserEntity();
    user.setId(clientId);

    roleUser.setUser(user);
    roleUser.setRole(role);

    RoleUserEntity addRoleUser = roleUserRepository.save(roleUser);
    return factory.getImpl(addRoleUser);
  }

  @Override
  public ICreatedAccount createAccountClient(long clientId) {
    UserAccountEntity userAccount = new UserAccountEntity();

    UserEntity user = new UserEntity();
    user.setId(clientId);

    userAccount.setUser(user);

    UserAccountEntity saveAccount = userAccountRepository.save(userAccount);

    return factory.getImpl(userAccount);
  }
}
