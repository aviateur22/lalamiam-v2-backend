package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.exception.ConflictException;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.useCase.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.useCase.ProfessionalInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.IProfessionalInscriptionConfirmationInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.useCase.ProfessionalInscriptionConfirmationUseCase;
import com.ctoutweb.lalamiam.infra.dto.auth.*;
import com.ctoutweb.lalamiam.infra.exception.AuthException;
import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.service.helper.AuthServiceHelper;
import com.ctoutweb.lalamiam.infra.core.mapper.ProfessionalInscriptionInputMapper;
import com.ctoutweb.lalamiam.infra.core.mapper.ClientInscriptionInputMapper;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.core.mapper.ProfessionalRegisterConfirmationInputMapper;
import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.IMessageResponse;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipal;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.service.*;
import jakarta.transaction.Transactional;
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
  private final ProfessionalInscriptionConfirmationUseCase professionalInscriptionConfirmationUseCase;
  private final ClientInscriptionInputMapper clientInscriptionMapper;
  private final ProfessionalInscriptionInputMapper professionalInscriptionMapper;
  private final ProfessionalRegisterConfirmationInputMapper professionalRegisterConfirmationInputMapper;
  private final AuthServiceHelper authServiceHelper;


  public AuthServiceImpl(
          IApiLanguage apiLanguage,
          IMessageService messageService,
          IJwtService jwtService,
          AuthenticationManager authenticationManager,
          ClientInscriptionUseCase clientInscriptionUseCase,
          Factory factory,
          ProfessionalInscriptionUseCase professionalInscriptionUseCase,
          ClientInscriptionInputMapper clientInscriptionMapper,
          ICryptoService cryptoService,
          ProfessionalInscriptionConfirmationUseCase professionalInscriptionConfirmationUseCase,
          ProfessionalInscriptionInputMapper professionalInscriptionMapper,
          ProfessionalRegisterConfirmationInputMapper professionalRegisterConfirmationInputMapper,
          AuthServiceHelper authServiceHelper) {
    this.apiLanguage = apiLanguage;
    this.messageService = messageService;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.clientInscriptionUseCase = clientInscriptionUseCase;
    this.factory = factory;
    this.professionalInscriptionUseCase = professionalInscriptionUseCase;
    this.clientInscriptionMapper = clientInscriptionMapper;
    this.cryptoService = cryptoService;
    this.professionalInscriptionConfirmationUseCase = professionalInscriptionConfirmationUseCase;
    this.professionalInscriptionMapper = professionalInscriptionMapper;
    this.professionalRegisterConfirmationInputMapper = professionalRegisterConfirmationInputMapper;

    this.authServiceHelper = authServiceHelper;
  }

  @Override
  @Transactional
  public IMessageResponse registerClient(RegisterClientDto dto) throws ConflictException {
      String hashPassword = cryptoService.hashText(dto.password());
      IClientInscriptionInput clientInscriptionInformation = clientInscriptionMapper.map(dto, hashPassword);

      // Valide l'envoie d'email apres l'inscription
      clientInscriptionUseCase.setEmailToSend(true);
      ClientInscriptionUseCase.Input input = ClientInscriptionUseCase.Input.getInput(clientInscriptionInformation);
      ClientInscriptionUseCase.Output output = clientInscriptionUseCase.execute(input);

      return factory.getMessageResponseImpl(output.getOutputBoundary().getResponseMessage());
  }

  @Override
  @Transactional
  public IMessageResponse registerProfessional(RegisterProfessionalDto dto) {

    IProfessionalInscriptionInput professionalInscriptionInput = professionalInscriptionMapper.map(dto);
    ProfessionalInscriptionUseCase.Input input = ProfessionalInscriptionUseCase.Input.getInput(professionalInscriptionInput);
    ProfessionalInscriptionUseCase.Output output = professionalInscriptionUseCase.execute(input);

    return factory.getMessageResponseImpl(output.getOutputBoundary().getResponseMessage());
  }

  @Override
  @Transactional
  public IMessageResponse registerConfirmByProfessional(RegisterConfirmByProfessionalDto dto) {

    // Vérification des tokens envoyé
    boolean aretokenValid = authServiceHelper.areProfessionalRegisterTokensValid(dto.email(), dto.emailToken(), dto.urlToken());

    if(!aretokenValid)
      throw new BadRequestException(messageService.getMessage("professional.confirmation.account.token.error"));

    IProfessionalInscriptionConfirmationInput professionalInscriptionConfirmationInput = professionalRegisterConfirmationInputMapper.map(dto);
    ProfessionalInscriptionConfirmationUseCase.Input input = ProfessionalInscriptionConfirmationUseCase.Input.getInput(professionalInscriptionConfirmationInput);
    ProfessionalInscriptionConfirmationUseCase.Output output = professionalInscriptionConfirmationUseCase.execute(input);

    return factory.getMessageResponseImpl(output.getOutputBoundary().getResponseMessage());
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

    jwtService.saveJwt(userPrincipal.getId(), jwt, dto.email());

    String responseMessage = messageService.getMessage("login.success").replace(
            "!%!nickname!%!", userPrincipal.getUsername());

    return new LoginResponseDto(
            jwt.getJwtToken(),
            userPrincipal.getEmail(),
            userPrincipal.getId(),
            userPrincipal.getAuthorities().stream().map(authority->authority.getAuthority()).collect(Collectors.toList()),
            responseMessage);
  }

  @Override
  public IMessageResponse lostPasswordMailing(HandleLostPasswordDto dto) {
    return authServiceHelper.lostPasswordMailing(dto.email());
  }

  @Override
  @Transactional
  public IMessageResponse reinitializeLostPassword(ReinitializeLostPasswordDto dto) {
    authServiceHelper.reinitializeLostPassword(dto);
    return factory.getMessageResponseImpl(messageService.getMessage("change.password.success"));
  }

  @Override
  @Transactional
  public IMessageResponse logout(LogoutDto dto) {
    jwtService.deleteJwtByUserEmail(dto.email());
    return factory.getMessageResponseImpl(messageService.getMessage("logout"));
  }

  @Override
  public IAppParam getAppParamter() {
    return factory.getAppParamImpl(apiLanguage.getValidatedLanguage());
  }
}
