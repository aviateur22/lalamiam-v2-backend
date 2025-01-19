package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.adapter.professionalInscription.*;
import com.ctoutweb.lalamiam.core.exception.ConflictException;
import com.ctoutweb.lalamiam.core.useCase.impl.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.impl.ProfessionalInscriptionUseCase;
import com.ctoutweb.lalamiam.infra.dto.LoginDto;
import com.ctoutweb.lalamiam.infra.dto.LoginResponseDto;
import com.ctoutweb.lalamiam.infra.exception.AuthException;
import com.ctoutweb.lalamiam.infra.mapper.boundaries.ProfessionalInscriptionBoundaryInputMapper;
import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.mapper.boundaries.ClientInscriptionBoundaryInputMapper;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipal;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.service.IAuthService;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IJwtService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements IAuthService {

  private final IApiLanguage apiLanguage;
  private final ICryptoService cryptoService;
  private final IMessageService messageService;
  private final IJwtService jwtService;
  private final Factory factory;
  private final AuthenticationManager authenticationManager;
  private final ClientInscriptionUseCase clientInscriptionUseCase;
  private final ProfessionalInscriptionUseCase professionalInscriptionUseCase;
  private final ClientInscriptionBoundaryInputMapper clientInscriptionMapper;
  private final ProfessionalInscriptionBoundaryInputMapper professionalInscriptionMapper;

  public AuthServiceImpl(
          IApiLanguage apiLanguage,
          IMessageService messageService,
          IJwtService jwtService,
          AuthenticationManager authenticationManager,
          ClientInscriptionUseCase clientInscriptionUseCase,
          Factory factory,
          ProfessionalInscriptionUseCase professionalInscriptionUseCase,
          ClientInscriptionBoundaryInputMapper clientInscriptionMapper,
          ICryptoService cryptoService,
          ProfessionalInscriptionBoundaryInputMapper professionalInscriptionMapper) {
    this.apiLanguage = apiLanguage;
    this.messageService = messageService;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.clientInscriptionUseCase = clientInscriptionUseCase;
    this.factory = factory;
    this.professionalInscriptionUseCase = professionalInscriptionUseCase;
    this.clientInscriptionMapper = clientInscriptionMapper;
    this.cryptoService = cryptoService;
    this.professionalInscriptionMapper = professionalInscriptionMapper;
  }

  @Override
  @Transactional
  public void registerClient(RegisterClientDto dto) throws ConflictException {
      String hashPassword = cryptoService.hashText(dto.password());
      IBoundaryInputAdapter boundaryInputAdapter = clientInscriptionMapper.map(dto, hashPassword);
      ClientInscriptionUseCase.Input input = ClientInscriptionUseCase.Input.getUseCaseInput(boundaryInputAdapter);
      ClientInscriptionUseCase.Output output = clientInscriptionUseCase.execute(input);
  }

  @Override
  @Transactional
  public void registerProfessional(RegisterProfessionalDto dto) {
    String hashPassword = cryptoService.hashText(dto.password());
    IBoundariesAdapter.IBoundaryInputAdapter boundaryInputAdapter = professionalInscriptionMapper.map(dto, hashPassword);
    ProfessionalInscriptionUseCase.Input input = ProfessionalInscriptionUseCase.Input.getUseCaseInput(boundaryInputAdapter);
    ProfessionalInscriptionUseCase.Output output = professionalInscriptionUseCase.execute(input);
  }

  @Override
  @Transactional(dontRollbackOn = AuthException.class)
  public LoginResponseDto login(LoginDto dto) {
    // Destruction JWT existant
    jwtService.deleteJwtByUserEmail(dto.email());

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

    IJwtIssue jwt = jwtService.generate(userPrincipal);

    jwtService.saveJwt(userPrincipal.id(), jwt, dto.email());

    String responseMessage = messageService.getMessage("login.success").replace(
            "!%!nickname!%!", userPrincipal.getUsername());

    return new LoginResponseDto(
            jwt.getJwtToken(),
            userPrincipal.email(),
            userPrincipal.id(),
            userPrincipal.getAuthorities().stream().map(authority->authority.getAuthority()).collect(Collectors.toList()),
            responseMessage);
  }

  @Override
  public IAppParam getAppParamter() {
    return factory.getAppParamImpl(apiLanguage.getValidatedLanguage());
  }
}
