package entity;

import entity.captcha.strategy.impl.context.CaptchaContextImpl;
import entity.captcha.strategy.ICaptchaStrategy;
import entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import entity.captcha.strategy.impl.strategy.CaptchaImageStrategyImpl;
import entity.captcha.strategy.impl.strategy.CaptchaTextStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import provider.ICaptchaConfiguration;
import provider.ICaptchaRepository;
import provider.ICryptographicService;
import provider.IMessageService;

public class CaptchaContextTest {
  @Mock
  IMessageService messageService;
  @Mock
  ICaptchaStrategy captchaStrategy;

  @Mock
  ICaptchaRepository captchaRepository;

  @Mock
  ICryptographicService cryptographicService;

  @Mock
  ICaptchaConfiguration captchaConfiguration;
  @InjectMocks
  CaptchaContextImpl captchaContext;


  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);

  }

  @Test
  public void setCaptchaStrategy_should_set_captcha_calcul_strategy() {
    /**
     * given
     */
    ICaptchaStrategy captchaCalculStrategy = new CaptchaCalculStrategyImpl(messageService, cryptographicService, captchaRepository, captchaConfiguration);


    /**
     * when
     */
    captchaContext.setCaptchaStrategy(captchaCalculStrategy);

    /**
     * then
     */
    Assertions.assertEquals(CaptchaCalculStrategyImpl.class, captchaContext.getCaptchaStrategy().getClass());

  }

  @Test
  public void setCaptchaStrategy_should_set_captcha_text_strategy() {
    /**
     * given
     */
    ICaptchaStrategy captchaTextStrategy = new CaptchaTextStrategyImpl(messageService, cryptographicService, captchaRepository, captchaConfiguration);


    /**
     * when
     */
    captchaContext.setCaptchaStrategy(captchaTextStrategy);

    /**
     * then
     */
    Assertions.assertEquals(CaptchaTextStrategyImpl.class, captchaContext.getCaptchaStrategy().getClass());

  }

  @Test
  public void setCaptchaStrategy_should_set_captcha_image_strategy() {
    /**
     * given
     */
    ICaptchaStrategy captchaImageStrategy = new CaptchaImageStrategyImpl(messageService, cryptographicService, captchaRepository, captchaConfiguration);


    /**
     * when
     */
    captchaContext.setCaptchaStrategy(captchaImageStrategy);

    /**
     * then
     */
    Assertions.assertEquals(CaptchaImageStrategyImpl.class, captchaContext.getCaptchaStrategy().getClass());

  }

  @Test
  public void execute_should_execute_the_generateCaptcha_method_of_CaptchaStrategy() {
    /**
     * given
     */
    captchaContext.setCaptchaStrategy(captchaStrategy);

    /**
     * when
     */
    captchaContext.execute();

    /**
     * then
     */
    Mockito.verify(captchaStrategy, Mockito.times(1)).generateCaptcha();
  }

}
