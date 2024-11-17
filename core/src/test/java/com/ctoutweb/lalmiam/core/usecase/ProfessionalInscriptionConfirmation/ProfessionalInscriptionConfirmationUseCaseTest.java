package com.ctoutweb.lalmiam.core.usecase.ProfessionalInscriptionConfirmation;

import com.ctoutweb.lalamiam.core.adapter.professionalInscriptionConfirmation.IBoundariesAdapter;
import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.common.IProfessional;
import com.ctoutweb.lalamiam.core.entity.common.impl.ProfessionalImpl;
import com.ctoutweb.lalamiam.core.entity.common.impl.TokenToValidateImpl;
import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.IProfessionalInscriptionConfirmation;
import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import com.ctoutweb.lalamiam.core.factory.CommonFactory;
import org.mockito.Mockito;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ctoutweb.lalamiam.core.provider.INotificationService;
import com.ctoutweb.lalamiam.core.provider.IProfessionalInscriptionConfirmationRepository;
import com.ctoutweb.lalamiam.core.provider.IValidateTokenService;
import com.ctoutweb.lalamiam.core.useCase.impl.ProfessionalInscriptionConfirmationUseCase;

import java.time.LocalDateTime;
import java.util.Optional;

public class ProfessionalInscriptionConfirmationUseCaseTest {
  @Mock
  IMessageService messageService;
  @Mock
  INotificationService notificationService;
  @Mock
  IProfessionalInscriptionConfirmationRepository professionalInscriptionConfirmationRepository;
  @Mock
  IValidateTokenService validateTokenService;
  ProfessionalInscriptionConfirmationUseCase professionalInscriptionConfirmationUseCase;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);

    professionalInscriptionConfirmationUseCase = new ProfessionalInscriptionConfirmationUseCase(
            professionalInscriptionConfirmationRepository,
            notificationService,
            messageService,
            validateTokenService);
  }

  @Test
  public void ProfessionalInscriptionConfirmationUseCase_should_throw_when_professional_data_are_null() {
    /**
     * given
     */
    String expectedErrorMessage = "pas de données pour le professionel";
    IBoundariesAdapter.IBoundaryInputAdapter professionalConfirmationInformationAdapter = DataForTest.fakeProfessionalConfirmationInformationAdapter();
    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalConfirmationToken(Mockito.any())).thenReturn(Optional.empty());
    Mockito.when(messageService.getMessage("professional.account.confirmed.error")).thenReturn(expectedErrorMessage);
    /**
     * when
     */
    ProfessionalInscriptionConfirmationUseCase.Input input = ProfessionalInscriptionConfirmationUseCase.Input.getUseCaseInput(professionalConfirmationInformationAdapter);
    Exception exception = Assertions.assertThrows(
            BadRequestException.class,
            ()-> professionalInscriptionConfirmationUseCase.execute(input)
    );
    String actualErrorMessage = exception.getMessage();


    /**
     * then
     */
    Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
  }

  @Test
  public void ProfessionalInscriptionConfirmationUseCase_should_throw_when_professional_account_already_confirmed() {
    /**
     * given
     */
    String expectedErrorMessage = "compte pro déja confirmé";
    IBoundariesAdapter.IBoundaryInputAdapter professionalConfirmationInformationAdapter = DataForTest.fakeProfessionalConfirmationInformationAdapter();

    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalByEmail(Mockito.any()))
            .thenReturn(Optional.of(DataForTest.fakeProfessionalWithAccountConfirmed()));

    Mockito.when(messageService.getMessage("professional.account.already.confirmed"))
            .thenReturn(expectedErrorMessage);
    /**
     * when
     */
    ProfessionalInscriptionConfirmationUseCase.Input input = ProfessionalInscriptionConfirmationUseCase
            .Input
            .getUseCaseInput(professionalConfirmationInformationAdapter);

    Exception exception = Assertions.assertThrows(
            BadRequestException.class,
            ()-> professionalInscriptionConfirmationUseCase.execute(input)
    );
    String actualErrorMessage = exception.getMessage();


    /**
     * then
     */
    Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
  }

  @Test
  public void ProfessionalInscriptionConfirmationUseCase_should_throw_when_confirmation_token_not_found() {
    /**
     * given
     */
    String expectedErrorMessage = "token pour confirmer compte professionel absent";
    IBoundariesAdapter.IBoundaryInputAdapter professionalConfirmationInformationAdapter = DataForTest.fakeProfessionalConfirmationInformationAdapter();

    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalByEmail(Mockito.any()))
            .thenReturn(Optional.of(DataForTest.fakeProfessionalWithAccountNotConfirmed()));

    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalConfirmationToken(Mockito.any()))
            .thenReturn(Optional.empty());

    Mockito.when(messageService.getMessage("professional.account.confirmed.token.error"))
            .thenReturn(expectedErrorMessage);
    /**
     * when
     */
    ProfessionalInscriptionConfirmationUseCase.Input input = ProfessionalInscriptionConfirmationUseCase
            .Input
            .getUseCaseInput(professionalConfirmationInformationAdapter);
    Exception exception = Assertions.assertThrows(
            BadRequestException.class,
            ()-> professionalInscriptionConfirmationUseCase.execute(input)
    );
    String actualErrorMessage = exception.getMessage();


    /**
     * then
     */
    System.out.println(actualErrorMessage);
    Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
  }

  @Test
  public void ProfessionalInscriptionConfirmationUseCase_should_throw_when_activatedLimiteDate_is_passed() {
  /**
   * given
   */
    String expectedErrorMessage = "Date de confirmation dépassé";
    IBoundariesAdapter.IBoundaryInputAdapter professionalConfirmationInformationAdapter = DataForTest.fakeProfessionalConfirmationInformationAdapter();

    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalByEmail(Mockito.any()))
            .thenReturn(Optional.of(DataForTest.fakeProfessionalWithAccountNotConfirmed()));

    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalConfirmationToken(Mockito.any()))
            .thenReturn(Optional.of(DataForTest.fakeProfessionalConfirmationTokenAdapter()));

    Mockito.when(messageService.getMessage("professional.account.limit.date.expired"))
            .thenReturn(expectedErrorMessage);
    /**
     * when
     */
    ProfessionalInscriptionConfirmationUseCase.Input input = ProfessionalInscriptionConfirmationUseCase
            .Input
            .getUseCaseInput(professionalConfirmationInformationAdapter);

    Exception exception = Assertions.assertThrows(
            BadRequestException.class,
            ()-> professionalInscriptionConfirmationUseCase.execute(input)
    );
    String actualErrorMessage = exception.getMessage();


    /**
     * then
     */
    Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
  }

  @Test
  public void ProfessionalInscriptionConfirmationUseCase_should_throw_when_validate_token_are_false() {
    /**
     * given
     */
    String expectedErrorMessage = "Erreur sur la confirmation des tokens";
    TokenToValidateImpl emailTokenToValidate = DataForTest.fakeTokenWithHashToken("emaiTokenpublic", "emailtokenprivate");
    TokenToValidateImpl urlTokenToValidate = DataForTest.fakeTokenWithHashToken("urlpublictoken", "urlprivatetoken");

    IBoundariesAdapter.IBoundaryInputAdapter professionalConfirmationInformationAdapter = DataForTest.fakeProfessionalConfirmationInformationAdapter();

    // Mock récupération professionnel
    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalByEmail(Mockito.any()))
            .thenReturn(Optional.of(DataForTest.fakeProfessionalWithAccountNotConfirmed()));

    // Mock récupération token de confirmation
    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalConfirmationToken(Mockito.any()))
            .thenReturn(Optional.of(DataForTest.fakeProfessionalConfirmationTokenAdapterWithConfirmDateValid()));


    Mockito.when(validateTokenService.validateHashToken(emailTokenToValidate))
            .thenReturn(false);

    Mockito.when(validateTokenService.validateHashToken(urlTokenToValidate))
            .thenReturn(false);

    Mockito.when(messageService.getMessage("professional.confirmation.account.token.error"))
            .thenReturn(expectedErrorMessage);
    /**
     * when
     */
    ProfessionalInscriptionConfirmationUseCase.Input input = ProfessionalInscriptionConfirmationUseCase
            .Input
            .getUseCaseInput(professionalConfirmationInformationAdapter);

    Exception exception = Assertions.assertThrows(
            BadRequestException.class,
            ()-> professionalInscriptionConfirmationUseCase.execute(input)
    );
    String actualErrorMessage = exception.getMessage();


    /**
     * then
     */
    Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
  }


  @Test
  public void findProfessionalByEmail_should_find_professional() {
    /**
     * given
     */
    IBoundariesAdapter.IBoundaryInputAdapter professionalConfirmationInformationAdapter = new IBoundariesAdapter.IBoundaryInputAdapter () {
      @Override
      public String getProfessionalEmailToken() {
        return null;
      }

      @Override
      public String getProfessionalUrlToken() {
        return null;
      }

      @Override
      public String getProfessionalEmail() {
        return null;
      }
    };
    String professionalEmail = professionalConfirmationInformationAdapter.getProfessionalEmail();

    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalByEmail(professionalEmail))
            .thenReturn(Optional.of(DataForTest.fakeProfessionalWithAccountNotConfirmed()));

    /**
     *when
     */
    IProfessional actualProfessionalInformation = professionalInscriptionConfirmationUseCase
            .findProfessionalInformationByEmail(professionalConfirmationInformationAdapter.getProfessionalEmail());

    IProfessional expectedProfessionalInformation = CommonFactory
            .getProfessionalImpl(DataForTest.fakeProfessionalWithAccountNotConfirmed());

    /**
     * then
     */
    Assertions.assertEquals(expectedProfessionalInformation.getProfessionalId(), actualProfessionalInformation.getProfessionalId());
    Assertions.assertEquals(expectedProfessionalInformation.getClientId(), actualProfessionalInformation.getClientId());
    Assertions.assertEquals(expectedProfessionalInformation.professionalAccountActivationAt(), actualProfessionalInformation.professionalAccountActivationAt());
    Assertions.assertEquals(expectedProfessionalInformation.isProfessionalAccountValidateByAdmin(), actualProfessionalInformation.isProfessionalAccountValidateByAdmin());
    Assertions.assertEquals(expectedProfessionalInformation.isProfessionalAccountConfirmed(), actualProfessionalInformation.isProfessionalAccountConfirmed());
  }

  @Test
  public void findProfessionalByEmail_should_return_null() {
    /**
     * given
     */
    IBoundariesAdapter.IBoundaryInputAdapter professionalConfirmationInformationAdapter = DataForTest.fakeProfessionalConfirmationInformationAdapter();
    String professionalEmail = professionalConfirmationInformationAdapter.getProfessionalEmail();
    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalByEmail(professionalEmail))
            .thenReturn(Optional.empty());

    /**
     *when
     */
    IProfessional actualProfessionalInformation = professionalInscriptionConfirmationUseCase
            .findProfessionalInformationByEmail(professionalConfirmationInformationAdapter.getProfessionalEmail());

    /**
     * then
     */
    Assertions.assertNull(actualProfessionalInformation);
  }

  @Test
  public void isProfessionalAccountActive_should_return_false() {
    /**
     * given
     */
    ProfessionalImpl professional = DataForTest.fakeProfessionalWithAccountNotActive();

    /**
     * then
     */
    boolean actualIsAccountActive = professionalInscriptionConfirmationUseCase.isProfessionalAccountConfirmed(professional);

    /**
     * then
     */
    Assertions.assertFalse(actualIsAccountActive);
  }

  @Test
  public void isProfessionalAccountActive_should_return_true() {
    /**
     * given
     */
    ProfessionalImpl professional = DataForTest.fakeProfessionalWithAccountActive();

    /**
     * then
     */
    boolean actualIsAccountActive = professionalInscriptionConfirmationUseCase.isProfessionalAccountConfirmed(professional);

    /**
     * then
     */
    Assertions.assertTrue(actualIsAccountActive);
  }

  @Test
  public void findProfessionalConfirmationToken_should_return_token() {
    /**
     * Given
     */
    Long professionalId = 1l;
    IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken professionalConfirmationTokenAdapter = DataForTest.fakeProfessionalConfirmationTokenAdapter();
    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalConfirmationToken(Mockito.any()))
            .thenReturn(Optional.of((professionalConfirmationTokenAdapter))
    );

    /**
     * then
     */
    IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken actualResponse = professionalInscriptionConfirmationUseCase.findProfessionalConfirmationToken(professionalId);
    IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken expectedResponse = DataForTest.fakeProfessionalConfirmationTokenAdapter();

    /**
     * Then
     */
    Assertions.assertEquals(expectedResponse.getActivatedLimitTime(), actualResponse.getActivatedLimitTime());
    Assertions.assertEquals(expectedResponse.getUrlHashToken(), actualResponse.getUrlHashToken());
    Assertions.assertEquals(expectedResponse.getEmailHashToken(), actualResponse.getEmailHashToken());
  }

  @Test
  public void findProfessionalConfirmationInformation_should_return_null() {
    /**
     * Given
     */
    Long professionalId = 1l;
    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalConfirmationToken(Mockito.any())).thenReturn(Optional.empty());

    /**
     * then
     */
    IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken actualResponse = professionalInscriptionConfirmationUseCase.findProfessionalConfirmationToken(professionalId);

    /**
     * Then
     */
    Assertions.assertNull(actualResponse);
  }

  @Test
  public void isActivatedLimitTimeValid_should_return_true() {
    /**
     * given
     */
    LocalDateTime activateLimitDateFomDatabase = LocalDateTime.of(2022, 10,11, 23, 59, 59);
    LocalDateTime actualDate = LocalDateTime.of(2022, 9,11, 23, 59, 59);

    /**
     * when
     */
    Boolean actualIsActivatedLimitTimeValid = professionalInscriptionConfirmationUseCase.isConfirmationLimitTimeValid(activateLimitDateFomDatabase, actualDate);
    Boolean expectedIsActivatedLimitTimeValid = true;

    /**
     * then
     */
    Assertions.assertTrue(actualIsActivatedLimitTimeValid);
  }

  @Test
  public void isActivatedLimitTimeValid_should_return_false() {
    /**
     * given
     */
      LocalDateTime activateLimitDateFomDatabase = LocalDateTime.of(2022, 10,11, 23, 59, 59);
      LocalDateTime actualDate = LocalDateTime.of(2022, 10,12, 23, 59, 59);

    /**
     * when
     */
      Boolean actualIsActivatedLimitTimeValid = professionalInscriptionConfirmationUseCase.isConfirmationLimitTimeValid(activateLimitDateFomDatabase, actualDate);

    /**
     * then
     */
      Assertions.assertFalse(actualIsActivatedLimitTimeValid);
  }

  @Test
  public void areProfessionalTokenValid_should_return_true() {
    /**
     * given
     */
    String urlTokenFromProfessional = "";
    String emailTokenFromProfessional = "";

    String urlHashToken = "";
    String emailHashToken = "";
    Mockito.when(validateTokenService.validateHashToken(Mockito.any())).thenReturn(true);

    /**
     * when
     */
    TokenToValidateImpl emailTokenWithHashToken = new TokenToValidateImpl(
            emailTokenFromProfessional, emailHashToken
    );

    TokenToValidateImpl urlTokenWithHashToken = new TokenToValidateImpl(
            urlTokenFromProfessional, urlHashToken
    );
    boolean actualResponse = professionalInscriptionConfirmationUseCase.areProfessionalTokenValid(emailTokenWithHashToken, urlTokenWithHashToken);
    boolean expectedResponse = true;


    /**
     * then
     */
    Assertions.assertTrue(actualResponse);
  }

  @Test
  public void areProfessionalTokenValid_should_return_false() {
    /**
     * given
     */
    String urlTokenFromProfessional = "";
    String emailTokenFromProfessional = "";

    String urlHashToken = "";
    String emailHashToken = "";
    Mockito.when(validateTokenService.validateHashToken(Mockito.any())).thenReturn(false);

    /**
     * when
     */
    TokenToValidateImpl emailTokenWithHashToken = new TokenToValidateImpl(
            emailTokenFromProfessional, emailHashToken
    );

    TokenToValidateImpl urlTokenWithHashToken = new TokenToValidateImpl(
            urlTokenFromProfessional, urlHashToken
    );
    boolean actualResponse = professionalInscriptionConfirmationUseCase.areProfessionalTokenValid(emailTokenWithHashToken, urlTokenWithHashToken);

    /**
     * then
     */
    Assertions.assertFalse(actualResponse);
  }

  @Test
  public void areProfessionaltokenValid_should_throw_when_professional_email_token_are_not_valid() {

    /**
     * given
     */
    TokenToValidateImpl emailTokenToValidate = DataForTest.fakeTokenWithHashToken("emaiTokenpublic", "emailtokenprivate");
    TokenToValidateImpl urlTokenToValidate = DataForTest.fakeTokenWithHashToken("", "");
    Mockito.when(validateTokenService.validateHashToken(emailTokenToValidate)).thenReturn(false);
    Mockito.when(validateTokenService.validateHashToken(urlTokenToValidate)).thenReturn(true);


    /**
     * when
     */
    boolean actualResponse = professionalInscriptionConfirmationUseCase.areProfessionalTokenValid(emailTokenToValidate, urlTokenToValidate);

    /**
     * then
     */
    Assertions.assertFalse(actualResponse);
  }

  @Test
  public void areProfessionaltokenValid_should_throw_when_professional_url_token_are_not_valid() {
/**
 * given
 */
    TokenToValidateImpl emailTokenToValidate = DataForTest.fakeTokenWithHashToken("emaiTokenpublic", "emailtokenprivate");
    TokenToValidateImpl urlTokenToValidate = DataForTest.fakeTokenWithHashToken("", "");
    Mockito.when(validateTokenService.validateHashToken(emailTokenToValidate)).thenReturn(true);
    Mockito.when(validateTokenService.validateHashToken(urlTokenToValidate)).thenReturn(false);


    /**
     * when
     */
    boolean actualResponse = professionalInscriptionConfirmationUseCase.areProfessionalTokenValid(emailTokenToValidate, urlTokenToValidate);

    /**
     * then
     */
    Assertions.assertFalse(actualResponse);
  }

  @Test
  public void areProfessionaltokenValid_should_throw_when_professional_url_and_email_token_are_not_valid() {
/**
 * given
 */
    TokenToValidateImpl emailTokenToValidate = DataForTest.fakeTokenWithHashToken("emaiTokenpublic", "emailtokenprivate");
    TokenToValidateImpl urlTokenToValidate = DataForTest.fakeTokenWithHashToken("", "");
    Mockito.when(validateTokenService.validateHashToken(emailTokenToValidate)).thenReturn(false);
    Mockito.when(validateTokenService.validateHashToken(urlTokenToValidate)).thenReturn(false);


    /**
     * when
     */
    boolean actualResponse = professionalInscriptionConfirmationUseCase.areProfessionalTokenValid(emailTokenToValidate, urlTokenToValidate);

    /**
     * then
     */
    Assertions.assertFalse(actualResponse);
  }

  @Test
  public void areProfessionaltokenValid_should_return_true() {
/**
 * given
 */
    TokenToValidateImpl emailTokenToValidate = DataForTest.fakeTokenWithHashToken("emaiTokenpublic", "emailtokenprivate");
    TokenToValidateImpl urlTokenToValidate = DataForTest.fakeTokenWithHashToken("", "");
    Mockito.when(validateTokenService.validateHashToken(emailTokenToValidate)).thenReturn(true);
    Mockito.when(validateTokenService.validateHashToken(urlTokenToValidate)).thenReturn(true);


    /**
     * when
     */
    boolean actualResponse = professionalInscriptionConfirmationUseCase.areProfessionalTokenValid(emailTokenToValidate, urlTokenToValidate);

    /**
     * then
     */
    Assertions.assertTrue(actualResponse);
  }

  @Test
  public void confirmProfessionalInscription() {
    /**
     * given
     */
    Long professionalId = 1l;

    /**
     * when
     */
    Mockito.doNothing().when(professionalInscriptionConfirmationRepository).confirmProfessionalInscription(professionalId);
  }


}
