package com.ctoutweb.lalamiam.infra.dto.client;

import com.ctoutweb.lalamiam.infra.annotation.custom.file.FileNotEmpty;
import com.ctoutweb.lalamiam.infra.annotation.custom.file.FilePdfIfNotEmpty;
import com.ctoutweb.lalamiam.infra.annotation.custom.file.PdfFile;
import com.ctoutweb.lalamiam.infra.dto.auth.UserCaptchaResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ClientRegisterAsProfessionalDto {
  @NotNull(message = "{firstname.missing}")
  @NotBlank(message = "{firstname.missing}")
  private String firstName;

  @NotNull(message = "{lastname.missing}")
  @NotBlank(message = "{lastname.missing}")
  private String lastName;

  @NotNull(message = "{phone.missing}")
  @NotBlank(message = "{phone.missing}")
  private String phone;

  @PdfFile(message = "{professional.register.file.1.error}")
  @FileNotEmpty(message = "{professional.register.file.1.error}")
  private MultipartFile file1;

  @FilePdfIfNotEmpty(message = "{professional.register.file.2.error}")
  private MultipartFile file2;
  @NotNull(message = "{captcha.response.missing}")
  private UserCaptchaResponseDto userCaptchaResponse;
}
