package com.ctoutweb.lalamiam.infra.model.captcha.strategy.mapper;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaImage;
import com.ctoutweb.lalamiam.infra.repository.entity.CaptchaImageEntity;
import org.springframework.stereotype.Component;

/**
 * Convertion vers un ICaptchaImage
 */
@Component
public class CaptchaImageMapper {
  private final Factory factory;

  public CaptchaImageMapper(Factory factory) {
    this.factory = factory;
  }

  public ICaptchaImage map(CaptchaImageEntity captchaImage) {
    return factory.getImpl(captchaImage.getId(), captchaImage.getName(), captchaImage.getPath(), captchaImage.getResponse());
  }
}
