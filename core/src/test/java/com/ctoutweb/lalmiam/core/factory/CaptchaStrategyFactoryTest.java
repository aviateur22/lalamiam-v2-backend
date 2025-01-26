//package com.ctoutweb.lalmiam.core.factory;
//
//import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;
//import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.factory.CaptchaStrategyFactoryImpl;
//import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
//import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaImageStrategyImpl;
//import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaTextStrategyImpl;
//import com.ctoutweb.lalamiam.core.entity.captcha.CaptchaType;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public class CaptchaStrategyFactoryTest {
//
//  @Mock
//  CaptchaTextStrategyImpl captchaTextStrategy;
//
//  @Mock
//  CaptchaCalculStrategyImpl captchaCalculStrategy;
//
//  @Mock
//  CaptchaImageStrategyImpl captchaImageStrategy;
//
//  CaptchaStrategyFactoryImpl captchaStrategyFactory;
//
//  @BeforeEach
//  public void init() {
//    MockitoAnnotations.openMocks(this);
//    captchaStrategyFactory = new CaptchaStrategyFactoryImpl(captchaTextStrategy, captchaCalculStrategy, captchaImageStrategy);
//  }
//
//  @Test
//  public void getCaptchaStrategy_should_return_CaptchaTextStrategy() {
//    /**
//     *
//     */
//    CaptchaType captchaType = CaptchaType.TEXT;
//
//    /**
//     * when
//     */
//    ICaptchaStrategy actualCaptchaStrategy = captchaStrategyFactory.getCaptchaStrategy(captchaType);
//    ICaptchaStrategy exptectedCaptchaStrategy = captchaTextStrategy;
//
//    /**
//     * then
//     */
//    Assertions.assertEquals(exptectedCaptchaStrategy.getClass(), actualCaptchaStrategy.getClass());
//  }
//
//  @Test
//  public void getCaptchaStrategy_should_return_CaptchaCalculStrategy() {
//    /**
//     *
//     */
//    CaptchaType captchaType = CaptchaType.CALCUL;
//
//    /**
//     * when
//     */
//    ICaptchaStrategy actualCaptchaStrategy = captchaStrategyFactory.getCaptchaStrategy(captchaType);
//    ICaptchaStrategy exptectedCaptchaStrategy = captchaCalculStrategy;
//
//    /**
//     * then
//     */
//    Assertions.assertEquals(exptectedCaptchaStrategy.getClass(), actualCaptchaStrategy.getClass());
//  }
//
//  @Test
//  public void getCaptchaStrategy_should_return_CaptchaImageStrategy() {
//    /**
//     *
//     */
//    CaptchaType captchaType = CaptchaType.IMAGE;
//
//    /**
//     * when
//     */
//    ICaptchaStrategy actualCaptchaStrategy = captchaStrategyFactory.getCaptchaStrategy(captchaType);
//    ICaptchaStrategy exptectedCaptchaStrategy = captchaImageStrategy;
//
//    /**
//     * then
//     */
//    Assertions.assertEquals(exptectedCaptchaStrategy.getClass(), actualCaptchaStrategy.getClass());
//  }
//
//}
