package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.infra.dto.auth.RegisterProfessionalDto;
import com.ctoutweb.lalamiam.infra.core.model.ProfessionalRegisterInputImpl;
import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.auth.IRegisterFile;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.utility.FileUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
  private final ICryptoService cryptoService;
  private final Factory factory;
  List<IRegisterFile> validatedProfessionalFiles = new ArrayList<>();

  public ProfessionalInscriptionInputMapper(
          IMessageService messageService,
          ICryptoService cryptoService,
          Factory factory) {
    this.messageService = messageService;
    this.cryptoService = cryptoService;
    this.factory = factory;
  }

  public IProfessionalInscriptionInput map(RegisterProfessionalDto dto) {
    try {
      validatedProfessionalFiles.clear();
      MultipartFile registerFile1 = dto.getFile1();
      MultipartFile registerFile2 = dto.getFile2();

      if(isRegisterFileValid(registerFile1))
        addValidatedRegisterfileToList(registerFile1);

      if(isRegisterFileValid(registerFile2))
        addValidatedRegisterfileToList(registerFile2);

      // Hash du mot de passe
      String hashPassword = cryptoService.hashText(dto.getPassword());

    return new ProfessionalRegisterInputImpl(dto, hashPassword, validatedProfessionalFiles);

    } catch (IOException e) {
      LOGGER.error(()->String.format("Exception lors du chargement des fichiers %s", e));
      throw new BadRequestException(messageService.getMessage("professional.document.on.error"));
    }
  }

  /**
   * Valide si la prise en compte d'un fichier
   * @param uploadFile MultipartFile - Fichier a valider
   */
  public boolean isRegisterFileValid(MultipartFile uploadFile) {
    if(uploadFile.isEmpty()
            || uploadFile.getSize() <= 0
            || uploadFile.getName().equalsIgnoreCase(FILE_EMPTY_NAME))
      return false;
    return  true;
  }

  /**
   * Ajout d'un fichier dans la list des fichiers valides
   * @param uploadFile
   * @throws IOException
   */
  public void addValidatedRegisterfileToList(MultipartFile uploadFile) throws IOException {
    IRegisterFile registerFile = factory.getRegisterFileImpl(uploadFile.getInputStream(),
            uploadFile.getSize(), FileUtility.getFileExtension(uploadFile));
    validatedProfessionalFiles.add(registerFile);
  }
}
