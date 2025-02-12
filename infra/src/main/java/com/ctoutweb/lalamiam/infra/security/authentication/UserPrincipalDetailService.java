package com.ctoutweb.lalamiam.infra.security.authentication;

import com.ctoutweb.lalamiam.infra.mapper.UserPrincipalMapper;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailService implements UserDetailsService {

  private final IUserRepository userRepository;
  private final IMessageService messageService;
  private final UserPrincipalMapper userPrincipalMapper;
  public UserPrincipalDetailService(
          IUserRepository userRepository,
          IMessageService messageService,
          UserPrincipalMapper userPrincipalMapper) {
    this.userRepository = userRepository;
    this.messageService = messageService;
    this.userPrincipalMapper = userPrincipalMapper;
  }

  @Override
  public UserPrincipal loadUserByUsername(String email)  {
    UserEntity user = userRepository.findByEmail(email).orElse(null);

    if(user == null)
      return null;

    return userPrincipalMapper.map(user);
  }
}
