package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;
import com.ctoutweb.lalamiam.infra.core.model.ProfessionalRegisterInputImpl;
import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.auth.IRegisterFile;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.FILE_EMPTY_NAME;

@Component
/**
 * Mapper vers IProfessionalInscriptionInput
 * Suite a des exceptions en cas de fichier à NULL, un fichier optionnel N°2 est créé.
 * Si le nom du fichier N°2 porte le nom de la constante FILE_EMPTY_NAME, alors on ne prends pas en compte
 * ce fichier
 */
public class ProfessionalInscriptionInputMapper {
  private static final Logger LOGGER = LogManager.getLogger();
  private final IMessageService messageService;
  private final Factory factory;

  public ProfessionalInscriptionInputMapper(IMessageService messageService, Factory factory) {
    this.messageService = messageService;
    this.factory = factory;
  }

  public IProfessionalInscriptionInput map(RegisterProfessionalDto dto, String hashPassword) {
    try {
      List<IRegisterFile> professionalFiles = new ArrayList<>();

      professionalFiles.add(factory.getRegisterFileImpl(dto.getFile1().getInputStream(), dto.getFile1().getSize()));

      if(!dto.getFile2().getName().equalsIgnoreCase(FILE_EMPTY_NAME))
        professionalFiles.add(factory.getRegisterFileImpl(dto.getFile2().getInputStream(), dto.getFile2().getSize()));

    return new ProfessionalRegisterInputImpl(dto, hashPassword, professionalFiles);

    } catch (IOException e) {
      LOGGER.error(()->String.format("Exception lors du chargement des fichiers %s", e));
      throw new BadRequestException(messageService.getMessage("professional.document.on.error"));
    }
  }
}
