package com.ctoutweb.lalamiam.infra.model;

import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription;
import com.ctoutweb.lalamiam.infra.model.impl.*;
import com.ctoutweb.lalamiam.infra.repository.entity.RoleUserEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedClient;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedRole;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedAccount;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class Factory {


  public IApiLanguage getImpl(String language) {
    return new ApiLanguageImpl(language);
  }

  public IApiMessage getImpl(Properties properties) {
    return new ApiMessageImpl(properties);
  }

  public ICreatedClient getImlpl(UserEntity user) {
    return new CreatedClientImpl(user);
  }

  public ICreatedRole getImpl(RoleUserEntity roleUser) {
    return new CreatedRoleImpl(roleUser);
  }

  public ICreatedAccount getImpl(UserAccountEntity userAccount)  {
    return new CreatedAccountImpl(userAccount);
  }
}
