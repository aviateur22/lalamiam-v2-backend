package com.ctoutweb.lalamiam.infra.model.captcha.strategy.impl;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaImage;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaGeneration;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.mapper.CaptchaImageMapper;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ICaptchaImageRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class CaptchaImageStrategyImpl implements ICaptchaStrategy {
  private static final CaptchaType captchaType = CaptchaType.IMAGE;
  private static final CryptographyType cryptoType = CryptographyType.ENCRYPT;
  private final ICaptchaGeneration captchaGeneration;
  private final ICaptchaImageRepository captchaImageRepository;
  private final CaptchaImageMapper captchaImageMapper;
  private final Factory factory;

  public CaptchaImageStrategyImpl(
          ICaptchaGeneration captchaGeneration,
          ICaptchaImageRepository captchaImageRepository,
          CaptchaImageMapper captchaImageMapper,
          Factory factory
  ) {
    this.captchaGeneration = captchaGeneration;
    this.captchaImageRepository = captchaImageRepository;
    this.captchaImageMapper = captchaImageMapper;
    this.factory = factory;
  }

  @Override
  public ICaptcha generateCaptcha() throws IOException {
    // Liste des images de disponible
    List<ICaptchaImage> captchaImages = captchaImageRepository
            .findAll()
            .stream()
            .map(captchaImageMapper::map)
            .toList();

    List<File> captchaImageFiles = getCaptchaImageFiles();

    captchaGeneration
            .generate(captchaImages, captchaImageFiles)
            .cryptographyAndSaveResponse(cryptoType)
            .convertCaptchaQuestionToBase64Image();
    return factory.getImpl(captchaGeneration);
  }

  /**
   * Récupération des images disponible dans le folder image/captcha
   * pour les captchas de type image
   * @return List<File> - Liste des images disponible
   */
  public List<File> getCaptchaImageFiles() {
    String folderPath = "image/captcha"; // Update path to match module structure
    File folder = new File(folderPath);

    if (folder.exists() && folder.isDirectory()) {

      File[] filesArray = folder.listFiles((dir, name) -> {
        String lowercaseName = name.toLowerCase();
        return lowercaseName.endsWith(".jpg") ||
                lowercaseName.endsWith(".jpeg") ||
                lowercaseName.endsWith(".png");
      });

      return Arrays.stream(filesArray).toList();
    }
    return List.of();
  }
}
