package usecase.validateUserCaptchaResponse;

import common.DataForTest;
import entity.validateUserCaptchaResponse.impl.BoundaryInputImpl;
import adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryInputAdapter;
import adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryOutputAdapter;
import exception.ApplicationException;
import exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import provider.ICaptchaConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import provider.ICryptographicService;
import provider.IMessageService;
import provider.INotificationService;
import useCase.impl.ValidateCaptchaResponseUseCase;

import static common.DataForTest.*;

/**
 * Case d'utilisation - captcha
 */
public class ValidateUserCaptchaResponseUseCaseTest {
  @Mock
  ICaptchaConfiguration captchaConfiguration;
  @Mock
  IMessageService messageService;

  @Mock
  ICryptographicService cryptographicService;

  @Mock
  INotificationService notificationService;

  ValidateCaptchaResponseUseCase captchaValidateClientResponseUseCase;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);

    captchaValidateClientResponseUseCase = new ValidateCaptchaResponseUseCase(
            messageService,
            cryptographicService,
            notificationService,
            captchaConfiguration
    );

  }

  @Test
  public void ValidateUserCaptchaResponseUseCase_should_throw_BadRequestException_when_user_captcha_response_wrong() {
    /**
     * given
     */

    Mockito.when(captchaConfiguration.getCaptchaToken()).thenReturn("aaabbbb");
    Mockito.when(captchaConfiguration.getStringSeparator()).thenReturn("%");
    Mockito.when(cryptographicService.isHashValid(Mockito.any())).thenReturn(false);

    String exceptionMessage = "mauvaise réponse captcha";
    Mockito.when(messageService.getMessage("captcha.invalid.response")).thenReturn(exceptionMessage);
    IBoundaryInputAdapter validateUserCaptchaResponseInformationAdapter = fakeUserCaptchaResponseAdapter();

    /**
     * when
     */
    ValidateCaptchaResponseUseCase.Input validateUserCaptchaResponseUseCaseInput = ValidateCaptchaResponseUseCase.Input.getUseCaseInput(validateUserCaptchaResponseInformationAdapter);
    Exception exception = Assertions.assertThrows(BadRequestException.class, ()->captchaValidateClientResponseUseCase.execute(validateUserCaptchaResponseUseCaseInput));
    Assertions.assertEquals(exceptionMessage, exception.getMessage());
  }

  @Test
  public void ValidateUserCaptchaResponseUseCase_should_return_true_when_user_reponse_is_valid() {

    /**
     * given
     */

    Mockito.when(cryptographicService.isHashValid(Mockito.any())).thenReturn(true);
    Mockito.when(captchaConfiguration.getCaptchaToken()).thenReturn("aa");
    Mockito.when(captchaConfiguration.getStringSeparator()).thenReturn("%%");

    IBoundaryInputAdapter boundaryInputAdapter = DataForTest.fakeUserCaptchaResponseAdapter();
    ValidateCaptchaResponseUseCase.Input input = ValidateCaptchaResponseUseCase.Input.getUseCaseInput(boundaryInputAdapter);

    /**
     * when
     */
    ValidateCaptchaResponseUseCase.Output output = captchaValidateClientResponseUseCase.execute(input);
    IBoundaryOutputAdapter boundaryOutput = output.getOutputBoundaryAdapter();

    /**
     * then
     */
    Assertions.assertTrue(boundaryOutput.getIsClientResponseValid());



  }


  @Test
  public void concatanate_user_response_with_captcha_token_should_return_aaaaabbbbccccffff() {
/**
 * given
 */
    Mockito.when(captchaConfiguration.getCaptchaToken()).thenReturn("aaaaabbbbcccc");
    Mockito.when(captchaConfiguration.getStringSeparator()).thenReturn("%");

    IBoundaryInputAdapter validateUserCaptchaResponseAdapter = fakeUserCaptchaResponseAdapter();
    BoundaryInputImpl validateUserCaptchaResponse = BoundaryInputImpl.getBoundaryInputImpl(
            validateUserCaptchaResponseAdapter.getCaptchaResponseByUser(),
            validateUserCaptchaResponseAdapter.getHashOrDecrypteCaptchaResponse(),
            validateUserCaptchaResponseAdapter.getCryptographicType());

    /**
     * when
     */

    String actualResponse = captchaValidateClientResponseUseCase.AddCaptchaConfToClientResponse(validateUserCaptchaResponseAdapter.getCaptchaResponseByUser());
    String expectedResponse = captchaConfiguration.getCaptchaToken() + captchaConfiguration.getStringSeparator() + validateUserCaptchaResponse.getCaptchaResponseByUser() ;
    /**
     * then
     */
    Assertions.assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void ValidateUserCaptchaResponseUseCase_should_throw_ApplicationException_when_CaptchaConfigurationToken_not_present() {
    /**
     * given
     */

    Mockito.when(captchaConfiguration.getCaptchaToken()).thenReturn("");

    String exceptionMessage = "Données manquantes pour résoudre le captcha";

    Mockito.when(messageService.getMessage("captcha.data.error")).thenReturn(exceptionMessage);
    ValidateCaptchaResponseUseCase.Input input = new ValidateCaptchaResponseUseCase.Input(fakeUserCaptchaResponseAdapter());

    /**
     * When
     */
    Exception exception = Assertions.assertThrows(ApplicationException.class, ()-> captchaValidateClientResponseUseCase.execute(input));

    /**
     * Then
     */
    Assertions.assertEquals(exceptionMessage, exception.getMessage());


  }

  @Test
  public void ValidateUserCaptchaResponseUseCase_should_throw_ApplicationException_when_CaptchaConfigurationStringSeparator_not_present() {
    /**
     * given
     */

    Mockito.when(captchaConfiguration.getCaptchaToken()).thenReturn("");

    String exceptionMessage = "Données manquantes pour résoudre le captcha";

    Mockito.when(messageService.getMessage("captcha.data.error")).thenReturn(exceptionMessage);
    ValidateCaptchaResponseUseCase.Input input = new ValidateCaptchaResponseUseCase.Input(fakeUserCaptchaResponseAdapter());

    /**
     * When
     */
    Exception exception = Assertions.assertThrows(ApplicationException.class, ()-> captchaValidateClientResponseUseCase.execute(input));

    /**
     * Then
     */
    Assertions.assertEquals(exceptionMessage, exception.getMessage());


  }

  @Test
  void captcha_private_token_should_be_afbcdefgh() {
    /**
     * Données entrée du useCase
     */
    Mockito.when(captchaConfiguration.getCaptchaToken()).thenReturn("afbcdefgh");

    ValidateCaptchaResponseUseCase.Input input = new ValidateCaptchaResponseUseCase.Input(fakeUserCaptchaResponseAdapter());


    /**
     * When
     */
    ValidateCaptchaResponseUseCase captchaValidateClientResponseUseCase = new ValidateCaptchaResponseUseCase(messageService, cryptographicService, notificationService, captchaConfiguration);

    String actualPrivateCaptchaToken = captchaValidateClientResponseUseCase.getCaptchaToken();
    String expectedProvateCaptchaToken = "afbcdefgh";

    /**
     * Then
     */
    Assertions.assertEquals(expectedProvateCaptchaToken, actualPrivateCaptchaToken);
  }
}
