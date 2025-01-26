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
//import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IGeneratedCaptcha;
//import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;
//
//import java.time.LocalDateTime;
//
//
//public class CaptchaCalculStrategyImpl extends Captcha implements ICaptchaStrategy {
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
//  public CaptchaCalculStrategyImpl(
//          IMessageService messageService,
//          ICryptographicService cryptographicService,
//          ICaptchaRepository captchaRepository,
//          ICaptchaConfiguration captchaConfiguration
//  ) {
//    super(
//            captchaRepository,
//            cryptographicService,
//            captchaConfiguration
//    );
//    this.messageService = messageService;
//  }
//
//  @Override
//  public IGeneratedCaptcha generateCaptcha() {
//
//    final String CAPTCHA_TITLE = messageService.getMessage("captcha.calcul.title");
//
//    // Validité du captcha
//    LocalDateTime validityResponseDateTime = LocalDateTime.now().plusHours(1);
//
//    // Borne pour la génération d'un chiffre aléatoire
//    final int MIN_RANDAOM_INTEGER = 0;
//    final int MAX_RANDOM_INTEGER = 9;
//
//    // Calcul chiffre1
//    final int RANDOM_NUMBER_1 = this.generateRandomNumberInBetween(MIN_RANDAOM_INTEGER, MAX_RANDOM_INTEGER);
//
//    // Calcul chiffre 2
//    final int RANDOM_NUMBER_2 = this.generateRandomNumberInBetween(MIN_RANDAOM_INTEGER, MAX_RANDOM_INTEGER);
//
//    // Operation mathématique
//    final char RANDOM_MATH_OPERATION = this.generateRandomMathOperation();
//
//    // Génération de la question
//    String captchaQuestion = this.generateCaptchaQuestion(RANDOM_NUMBER_1, RANDOM_MATH_OPERATION, RANDOM_NUMBER_2);
//
//    // Génération de la réponse
//    int captchaResponse = this.calculateResult(
//            RANDOM_NUMBER_1,
//            RANDOM_MATH_OPERATION,
//            RANDOM_NUMBER_2,
//            messageService.getMessage("captcha.config.error")
//    );
//
//    // Cryptographie de la réponse
//    ICryptography.ICryptographySaveResult cryptographyCaptchaResponse = this.cryptographyCaptchaResponseAnsSave(
//            String.valueOf(captchaResponse),
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
