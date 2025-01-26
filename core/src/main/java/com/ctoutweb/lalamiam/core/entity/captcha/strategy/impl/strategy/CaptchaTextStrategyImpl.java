//package com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy;
//
//import com.ctoutweb.lalamiam.core.entity.captcha.Captcha;
//import com.ctoutweb.lalamiam.core.entity.captcha.impl.GeneratedCaptchaImpl;
//import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
//import com.ctoutweb.lalamiam.core.entity.cryptographic.ICryptography;
//import com.ctoutweb.lalamiam.core.provider.ICaptchaConfiguration;
//import com.ctoutweb.lalamiam.core.provider.ICaptchaRepository;
//import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
//import com.ctoutweb.lalamiam.core.provider.IMessageService;
//import com.ctoutweb.lalamiam.core.util.IntegerUtil;
//import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IGeneratedCaptcha;
//import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;
//
//import java.time.LocalDateTime;
//
//
//public class CaptchaTextStrategyImpl extends Captcha implements ICaptchaStrategy {
//
//  /**
//   * Type de cryptographie utilisée  pour la réponse du captcha
//   */
//  final CryptographicType CRYPTOGRAPHY_TYPE = CryptographicType.HASH;
//
//  /**
//   * La génération de la question du captcha est-elle à transformer en image
//   */
//  final boolean IS_GENERATED_QUESTION_TO_BE_TRANSFORM_IN_IMAGE = true;
//  private final IMessageService messageService;
//
//  public CaptchaTextStrategyImpl(
//          IMessageService messageService,
//          ICryptographicService cryptographicService,
//          ICaptchaRepository captchaRepository,
//          ICaptchaConfiguration captchaConfiguration
//  ) {
//    super(captchaRepository, cryptographicService, captchaConfiguration);
//    this.messageService = messageService;
//  }
//
//
//  @Override
//  public IGeneratedCaptcha generateCaptcha() {
//
//    // Validité du captcha
//    LocalDateTime validityResponseDateTime = LocalDateTime.now().plusHours(1);
//
//    // Titre du captcha
//    final String CAPTCHA_TITLE = messageService.getMessage("captcha.text.title");
//
//    // Génération de la question
//    final int RANDOM_WORD_LENGTH = IntegerUtil.generateNumberBetween(7, 10);
//
//    String captchaQuestion = this.generateRandomWord(RANDOM_WORD_LENGTH);
//
//    // La réponse est équivalente à la question
//    String captchaResponse = captchaQuestion;
//
//    // Cryptographie de la réponse
//    ICryptography.ICryptographySaveResult cryptographyCaptchaResponse = this.cryptographyCaptchaResponseAnsSave(
//            captchaResponse,
//            CRYPTOGRAPHY_TYPE,
//            validityResponseDateTime
//    );
//
//    // Donnnées du captcha
//    return GeneratedCaptchaImpl.getGenerateCaptchaImpl(
//            CAPTCHA_TITLE,
//            cryptographyCaptchaResponse.getResponseId(),
//            captchaQuestion,
//            IS_GENERATED_QUESTION_TO_BE_TRANSFORM_IN_IMAGE
//    );
//  }
//}
