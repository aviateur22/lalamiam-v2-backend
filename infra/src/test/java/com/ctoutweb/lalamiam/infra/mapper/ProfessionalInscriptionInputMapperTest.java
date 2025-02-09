package com.ctoutweb.lalamiam.infra.mapper;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.infra.core.mapper.ProfessionalInscriptionInputMapper;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.FILE_EMPTY_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfessionalInscriptionInputMapperTest {
  @Mock
  IMessageService messageService;
  @Mock
  ICryptoService cryptoService;

  Factory factory = new Factory();;
  ProfessionalInscriptionInputMapper professionalInscriptionInputMapper;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void map_should_be_valid() {
    /**
     * when no files avail
     */
    professionalInscriptionInputMapper = new ProfessionalInscriptionInputMapper(messageService, cryptoService, factory);
    IProfessionalInscriptionInput result1 = professionalInscriptionInputMapper.map(getDtoWithEmptyFile());

    /**
     * when 1 files avail
     */
    professionalInscriptionInputMapper = new ProfessionalInscriptionInputMapper(messageService, cryptoService, factory);
    IProfessionalInscriptionInput result2 = professionalInscriptionInputMapper.map(getDtoWith1FileAvail());

    /**
     * when 2 files avails
     */
    professionalInscriptionInputMapper = new ProfessionalInscriptionInputMapper(messageService, cryptoService, factory);
    IProfessionalInscriptionInput result3 = professionalInscriptionInputMapper.map(getDtoWith2FilesAvail());

    /**
     * then -  when 0 file avail
     */
    assertEquals(0, result1.getProfessionalInscriptionDocuments().size());
    assertEquals(1, result2.getProfessionalInscriptionDocuments().size());
    assertEquals(2, result3.getProfessionalInscriptionDocuments().size());
  }

  private RegisterProfessionalDto getDtoWithEmptyFile() {
    RegisterProfessionalDto registerProfessionalDto = new RegisterProfessionalDto();
    registerProfessionalDto.setEmail("test@gmail");
    registerProfessionalDto.setNickname("nickname");
    registerProfessionalDto.setLastName("lastName");
    registerProfessionalDto.setPassword("password");
    registerProfessionalDto.setFirstName("firstName");
    registerProfessionalDto.setPhone("phone");
    registerProfessionalDto.setFile1(getEmptyFile());
    registerProfessionalDto.setFile2(getEmptyFile());
    return registerProfessionalDto;
  }

  private RegisterProfessionalDto getDtoWith1FileAvail() {
    RegisterProfessionalDto registerProfessionalDto = new RegisterProfessionalDto();
    registerProfessionalDto.setEmail("test@gmail");
    registerProfessionalDto.setNickname("nickname");
    registerProfessionalDto.setLastName("lastName");
    registerProfessionalDto.setPassword("password");
    registerProfessionalDto.setFirstName("firstName");
    registerProfessionalDto.setPhone("phone");
    registerProfessionalDto.setFile1(getNotEmptyFile());
    registerProfessionalDto.setFile2(getEmptyFile());
    return registerProfessionalDto;
  }

  private RegisterProfessionalDto getDtoWith2FilesAvail() {
    RegisterProfessionalDto registerProfessionalDto = new RegisterProfessionalDto();
    registerProfessionalDto.setEmail("test@gmail");
    registerProfessionalDto.setNickname("nickname");
    registerProfessionalDto.setLastName("lastName");
    registerProfessionalDto.setPassword("password");
    registerProfessionalDto.setFirstName("firstName");
    registerProfessionalDto.setPhone("phone");
    registerProfessionalDto.setFile1(getNotEmptyFile());
    registerProfessionalDto.setFile2(getNotEmptyFile());
    return registerProfessionalDto;
  }

  private MultipartFile getEmptyFile() {
    return new MultipartFile() {
      @Override
      public String getName() {
        return null;
      }

      @Override
      public String getOriginalFilename() {
        return FILE_EMPTY_NAME;
      }

      @Override
      public String getContentType() {
        return null;
      }

      @Override
      public boolean isEmpty() {
        return true;
      }

      @Override
      public long getSize() {
        return 0;
      }

      @Override
      public byte[] getBytes() throws IOException {
        return new byte[0];
      }

      @Override
      public InputStream getInputStream() throws IOException {
        return null;
      }

      @Override
      public void transferTo(File dest) throws IOException, IllegalStateException {

      }
    };
  }

  private MultipartFile getNotEmptyFile() {
    return new MultipartFile() {
      @Override
      public String getName() {
        return "gete";
      }

      @Override
      public String getOriginalFilename() {
        return "gete.html";
      }

      @Override
      public String getContentType() {
        return null;
      }

      @Override
      public boolean isEmpty() {
        return false;
      }

      @Override
      public long getSize() {
        return 13480;
      }

      @Override
      public byte[] getBytes() throws IOException {
        return new byte[0];
      }

      @Override
      public InputStream getInputStream() throws IOException {
        return null;
      }

      @Override
      public void transferTo(File dest) throws IOException, IllegalStateException {

      }
    };
  }
}
