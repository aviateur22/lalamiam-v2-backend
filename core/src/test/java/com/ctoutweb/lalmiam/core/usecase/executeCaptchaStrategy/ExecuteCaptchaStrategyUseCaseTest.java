package com.ctoutweb.lalmiam.core.usecase.executeCaptchaStrategy;

import com.ctoutweb.lalamiam.core.adapter.executeCaptchaStrategy.IBoundariesAdapter;
import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IGeneratedCaptcha;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaContext;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.context.CaptchaContextImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaImageStrategyImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaTextStrategyImpl;
import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.entity.cryptographic.ICryptography;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.ctoutweb.lalamiam.core.provider.ICaptchaConfiguration;
import com.ctoutweb.lalamiam.core.provider.ICaptchaRepository;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.impl.CreateCaptchaImageUseCase;
import com.ctoutweb.lalamiam.core.useCase.impl.ExecuteCaptchaStrategyUseCase;

import java.util.List;

public class ExecuteCaptchaStrategyUseCaseTest {
  @Mock
  IMessageService messageService;
  @Mock
  CreateCaptchaImageUseCase createCaptchaImageUseCase;
  @Mock
  ICryptographicService cryptographicService;
  @Mock
  ICaptchaRepository captchaRepository;
  @Mock
  ICaptchaConfiguration captchaConfiguration;
  ExecuteCaptchaStrategyUseCase executeCaptchaStrategyUseCase;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);

    ICaptchaStrategy defaultStrategyOnError = new CaptchaCalculStrategyImpl(
            messageService,
            cryptographicService,
            captchaRepository,
            captchaConfiguration
    );

    executeCaptchaStrategyUseCase = new ExecuteCaptchaStrategyUseCase(
            createCaptchaImageUseCase,
            messageService,
            defaultStrategyOnError);
  }

  @Test
  public void ExecuteCaptchaStrategyUseCase_with_CaptchaImageImpl_and_no_image_should_execute_CaptchaCalculImpl() {
    /**
     * given
     * On simule la strategy d'un captcha de type calcul image
     */
    IBoundariesAdapter.IBoundaryInputAdapter inputAdapter = new IBoundariesAdapter.IBoundaryInputAdapter() {
      @Override
      public ICaptchaContext getCaptchaContext() {
        return DataForTest.fakeCaptchaContextImageStrategy(messageService, cryptographicService, captchaRepository);
      }
    };

    ExecuteCaptchaStrategyUseCase.Input input = ExecuteCaptchaStrategyUseCase.Input.getUseCaseInput(inputAdapter);

    Mockito.when(messageService.getMessage("captcha.calcul.title")).thenReturn("capcha Calcul");
    Mockito.when(cryptographicService.cryptographyTextAndSave(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(
            new ICryptography.ICryptographySaveResult() {
              @Override
              public String getCryptographicText() {
                return "fddddd";
              }

              @Override
              public CryptographicType getCryptographicType() {
                return CryptographicType.HASH;
              }

              @Override
              public Long getResponseId() {
                return 1L;
              }
            }
    );

    /**
     * when
     */
    ExecuteCaptchaStrategyUseCase.Output output = executeCaptchaStrategyUseCase.execute(input);

    /**
     * then
     */
    Assertions.assertNotNull(output);

    IBoundariesAdapter.IBoundaryOutputAdapter outputAdapter = output.getOutputBoundaryAdapter();
    Assertions.assertEquals("capcha Calcul", outputAdapter.getGeneratedCaptcha().getCaptchaTitle());
  }

  @Test
  public void generateCaptcha_should_return_CaptchaImageGeneratedInformation_model_with_calcul_strategy() {
    /**
     * given
     * On simule la strategy d'un captcha de type calcul mathématique
     */
    CaptchaContextImpl captchaContext = DataForTest.fakeCaptchaContextCalculStrategy(messageService, cryptographicService);
    Mockito.when(messageService.getMessage("captcha.calcul.title")).thenReturn("capcha Calcul");
    Mockito.when(cryptographicService.cryptographyTextAndSave(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(
            new ICryptography.ICryptographySaveResult() {
              @Override
              public String getCryptographicText() {
                return "fddddd";
              }

              @Override
              public CryptographicType getCryptographicType() {
                return CryptographicType.HASH;
              }

              @Override
              public Long getResponseId() {
                return 1L;
              }
            }
    );

    /**
     * when
     */
     IGeneratedCaptcha captchaGeneratedInformation = executeCaptchaStrategyUseCase.generateCaptcha(captchaContext);

    /**
     * then
     */
    Assertions.assertTrue(captchaContext.getCaptchaStrategy().getClass().equals(CaptchaCalculStrategyImpl.class));
    Assertions.assertNotNull(captchaGeneratedInformation);
    Assertions.assertTrue(captchaGeneratedInformation.isQuestionToBeTransformedInImage());
    Assertions.assertEquals("capcha Calcul", captchaGeneratedInformation.getCaptchaTitle());
    Assertions.assertEquals(1L, captchaGeneratedInformation.getResponseId());
  }

  @Test
  public void generateCaptcha_should_return_CaptchaImageGeneratedInformation_model_with_text_strategy() {
    /**
     * given
     * On simule la strategy d'un captcha de type calcul mathématique
     */
    CaptchaContextImpl captchaContext = DataForTest.fakeCaptchaContextTextStrategy(messageService, cryptographicService);
    Mockito.when(messageService.getMessage("captcha.text.title")).thenReturn("capcha text");
    Mockito.when(cryptographicService.cryptographyTextAndSave(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(
            new ICryptography.ICryptographySaveResult() {
              @Override
              public String getCryptographicText() {
                return "fffff";
              }

              @Override
              public CryptographicType getCryptographicType() {
                return CryptographicType.HASH;
              }

              @Override
              public Long getResponseId() {
                return 1L;
              }
            }
    );

    /**
     * when
     */
    IGeneratedCaptcha captchaGeneratedInformation = executeCaptchaStrategyUseCase.generateCaptcha(captchaContext);

    /**
     * then
     */
    Assertions.assertTrue(captchaContext.getCaptchaStrategy().getClass().equals(CaptchaTextStrategyImpl.class));
    Assertions.assertNotNull(captchaGeneratedInformation);
    Assertions.assertTrue(captchaGeneratedInformation.isQuestionToBeTransformedInImage());
    Assertions.assertEquals("capcha text", captchaGeneratedInformation.getCaptchaTitle());
    Assertions.assertEquals(1L, captchaGeneratedInformation.getResponseId());
  }

  @Test
  public void generateCaptcha_should_return_CaptchaImageGeneratedInformation_model_with_image_strategy() {
    /**
     * given
     * On simule la strategy d'un captcha de type calcul mathématique
     */
    CaptchaContextImpl captchaContext = DataForTest.fakeCaptchaContextImageStrategy(messageService, cryptographicService, captchaRepository);
    Mockito.when(messageService.getMessage("captcha.image.title")).thenReturn("capcha image");
    Mockito.when(captchaRepository.foundCaptchaImages()).thenReturn(DataForTest.fakeCaptchaImages());
    Mockito.when(cryptographicService.cryptographyTextAndSave(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(
            new ICryptography.ICryptographySaveResult() {
              @Override
              public String getCryptographicText() {
                return "fffff";
              }

              @Override
              public CryptographicType getCryptographicType() {
                return CryptographicType.HASH;
              }

              @Override
              public Long getResponseId() {
                return 1L;
              }
            }
    );

    /**
     * when
     */
    IGeneratedCaptcha captchaGeneratedInformation = executeCaptchaStrategyUseCase.generateCaptcha(captchaContext);

    /**
     * then
     */
    Assertions.assertTrue(captchaContext.getCaptchaStrategy().getClass().equals(CaptchaImageStrategyImpl.class));
    Assertions.assertNotNull(captchaGeneratedInformation);
    Assertions.assertFalse(captchaGeneratedInformation.isQuestionToBeTransformedInImage());
    Assertions.assertEquals("capcha image", captchaGeneratedInformation.getCaptchaTitle());
    Assertions.assertEquals(1L, captchaGeneratedInformation.getResponseId());
  }

  @Test
  public void generateCaptcha_should_return_CaptchaImageGeneratedInformation_with_no_image_available_should_retrun_null() {
    /**
     * given
     * On simule la strategy d'un captcha de type calcul mathématique
     */
    CaptchaContextImpl captchaContext = DataForTest.fakeCaptchaContextImageStrategy(messageService, cryptographicService, captchaRepository);
    Mockito.when(messageService.getMessage("captcha.image.title")).thenReturn("capcha image");
    Mockito.when(captchaRepository.foundCaptchaImages()).thenReturn(List.of());
    Mockito.when(cryptographicService.cryptographyTextAndSave(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(
            new ICryptography.ICryptographySaveResult() {
              @Override
              public String getCryptographicText() {
                return "fffff";
              }

              @Override
              public CryptographicType getCryptographicType() {
                return CryptographicType.HASH;
              }

              @Override
              public Long getResponseId() {
                return 1L;
              }
            }
    );

    /**
     * when
     */
    IGeneratedCaptcha captchaGeneratedInformation = executeCaptchaStrategyUseCase.generateCaptcha(captchaContext);

    /**
     * then
     */
    Assertions.assertTrue(captchaContext.getCaptchaStrategy().getClass().equals(CaptchaImageStrategyImpl.class));
    Assertions.assertNull(captchaGeneratedInformation);
  }
}
