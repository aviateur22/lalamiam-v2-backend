package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.adapter.professionalInscription.*;
import com.ctoutweb.lalamiam.core.exception.ConflictException;
import com.ctoutweb.lalamiam.core.useCase.impl.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.impl.ProfessionalInscriptionUseCase;
import com.ctoutweb.lalamiam.infra.boundaries.ProfessionalInscriptionBoundaryInputMapper;
import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.boundaries.ClientInscriptionBoundaryInputMapper;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;
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
  private final ProfessionalInscriptionUseCase professionalInscriptionUseCase;
  private final ClientInscriptionBoundaryInputMapper clientInscriptionMapper;
  private final ProfessionalInscriptionBoundaryInputMapper professionalInscriptionMapper;

  public AuthServiceImpl(IApiLanguage apiLanguage, ClientInscriptionUseCase clientInscriptionUseCase, Factory factory, ProfessionalInscriptionUseCase professionalInscriptionUseCase, ClientInscriptionBoundaryInputMapper clientInscriptionMapper, ICryptoService cryptoService, ProfessionalInscriptionBoundaryInputMapper professionalInscriptionMapper) {
    this.apiLanguage = apiLanguage;
    this.clientInscriptionUseCase = clientInscriptionUseCase;
    this.factory = factory;
    this.professionalInscriptionUseCase = professionalInscriptionUseCase;
    this.clientInscriptionMapper = clientInscriptionMapper;
    this.cryptoService = cryptoService;
    this.professionalInscriptionMapper = professionalInscriptionMapper;
  }

  @Override
  public void registerClient(RegisterClientDto dto) throws ConflictException {
      String hashPassword = cryptoService.hashText(dto.password());
      IBoundaryInputAdapter boundaryInputAdapter = clientInscriptionMapper.map(dto, hashPassword);
      ClientInscriptionUseCase.Input input = ClientInscriptionUseCase.Input.getUseCaseInput(boundaryInputAdapter);
      ClientInscriptionUseCase.Output output = clientInscriptionUseCase.execute(input);

  }

  @Override
  public void registerProfessional(RegisterProfessionalDto dto) {
    String hashPassword = cryptoService.hashText(dto.password());
    IBoundariesAdapter.IBoundaryInputAdapter boundaryInputAdapter = professionalInscriptionMapper.map(dto, hashPassword);
    ProfessionalInscriptionUseCase.Input input = ProfessionalInscriptionUseCase.Input.getUseCaseInput(boundaryInputAdapter);
    ProfessionalInscriptionUseCase.Output output = professionalInscriptionUseCase.execute(input);
  }

  @Override
  public IAppParam getAppParamter() {
    return factory.getAppParamImpl(apiLanguage.getValidatedLanguage());
  }
}
