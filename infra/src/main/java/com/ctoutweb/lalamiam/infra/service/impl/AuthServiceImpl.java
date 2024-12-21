package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.exception.ConflictException;
import com.ctoutweb.lalamiam.core.useCase.impl.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.boundaries.ClientInscriptionBoundaryInput;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;
import com.ctoutweb.lalamiam.infra.service.IAuthService;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

  private final IApiLanguage apiLanguage;
  private final ICryptoService cryptoService;
  private final Factory factory;
  private final ClientInscriptionUseCase clientInscriptionUseCase;
  private final ClientInscriptionBoundaryInput clientInscriptionMapper;

  public AuthServiceImpl(IApiLanguage apiLanguage, ClientInscriptionUseCase clientInscriptionUseCase, Factory factory, ClientInscriptionBoundaryInput clientInscriptionMapper, ICryptoService cryptoService) {
    this.apiLanguage = apiLanguage;
    this.clientInscriptionUseCase = clientInscriptionUseCase;
    this.factory = factory;
    this.clientInscriptionMapper = clientInscriptionMapper;
    this.cryptoService = cryptoService;
  }

  @Override
  public void registerClient(RegisterClientDto dto) throws ConflictException {
      String hashPassword = cryptoService.hashText(dto.password());
      IBoundaryInputAdapter boundaryInputAdapter = clientInscriptionMapper.map(dto, hashPassword);
      ClientInscriptionUseCase.Input input = ClientInscriptionUseCase.Input.getUseCaseInput(boundaryInputAdapter);
      ClientInscriptionUseCase.Output output = clientInscriptionUseCase.execute(input);

  }

  @Override
  public void registerProfessional() {

  }

  @Override
  public IAppParam getAppParamter() {
    return factory.getAppParamImpl(apiLanguage.getValidatedLanguage());
  }
}
