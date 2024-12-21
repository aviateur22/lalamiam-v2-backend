package common;

import com.ctoutweb.lalamiam.core.adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.adapter.validateUserCaptchaResponse.IBoundariesAdapter;
import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.InputBoundaryAdapter;
import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.IAdminDisplayProfessionalDocument.IProfessionalDocument;
import com.ctoutweb.lalamiam.core.entity.createCaptchaImage.OutputBoundary;
import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha;
import com.ctoutweb.lalamiam.core.entity.common.IProfessional;
import com.ctoutweb.lalamiam.core.entity.common.impl.ProfessionalImpl;
import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.impl.entity.ProfessionalInscriptionDocumentImpl;
import com.ctoutweb.lalamiam.core.entity.common.impl.TokenToValidateImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.context.CaptchaContextImpl;
import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.entity.cryptographic.ICryptography;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription;
import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.IProfessionalInscriptionConfirmation;
import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.impl.boundary.BoundaryOutputImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaImageStrategyImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaTextStrategyImpl;
import com.ctoutweb.lalamiam.core.provider.ICaptchaConfiguration;
import com.ctoutweb.lalamiam.core.provider.ICaptchaRepository;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.core.provider.IMessageService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class DataForTest {

  public static ICaptchaConfiguration fakeCaptchaConfiguration() {
    return new ICaptchaConfiguration() {
      @Override
      public String getCaptchaToken() {
        return "dsdsdsdsdsdsd";
      }

      @Override
      public String getStringSeparator() {
        return "%!!%";
      }
    };
  }

  public static ICaptchaRepository fakeCaptchaRepository() {
    return new ICaptchaRepository() {
      @Override
      public List<ICaptcha.IAvailableCaptchaImage> foundCaptchaImages() {
        return null;
      }
    };
  }
  public static IMessageService fakeMessageService() {
    return new IMessageService() {
      @Override
      public String getMessage(String messageKey) {
        return null;
      }

      @Override
      public void logError(String message) {

      }

      @Override
      public void logInfo(String message) {

      }

      @Override
      public void logDebug(String message) {

      }
    };
  }
  public static com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter fakeProfessionalInscriptionInformationAdapter() {
    com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter professionalInscriptionInformationAdapter = new com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter() {


      @Override
      public String getUserName() {
        return "bobo";
      }

      @Override
      public String getFirstName() {
        return "my first name";
      }

      @Override
      public String getPhone() {
        return "06 23 27 41 01";
      }

      @Override
      public List<String> getDocuments() {
        return List.of(
                "document-1"
        );
     }

      @Override
      public String getHashPassword() {
        return "clientHashpassword";
      }

      @Override
      public String getEmail() {
        return "bob@marley.com";
      }

    };

    return professionalInscriptionInformationAdapter;
  }

  public static com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary.BoundaryOutputImpl fakeProfessionalInscriptionResult() {
    return new com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary.BoundaryOutputImpl(1l, "message réponse");
  }
  public static BoundaryOutputImpl professionalInscriptionConfirmmationResult(boolean isConfirmationValid) {
    return new BoundaryOutputImpl(isConfirmationValid, "fakeMessage");
  }
  public static com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter fakeClientInscriptionAdapter() {

    return new com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter() {

      @Override
      public String getHashPassword() {
        return "hashpassword";
      }

      @Override
      public String getEmail() {
        return "monemail@hotmail.fr";
      }

      @Override
      public String getUserName() {
        return "mon nom";
      }
    };
  }
  public static com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries.BoundaryOutputImpl userInscriptionResponse() {
    return new com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries.BoundaryOutputImpl("reponse message", 1l);
  }
  public static com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryOutputAdapter userInscriptionResponseAdapter() {
    return new com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryOutputAdapter() {
      @Override
      public Long getUserId() {
        return 1L;
      }

      @Override
      public String getResponseMessage() {
        return "reponse message";
      }
    };
  }
  public static IBoundariesAdapter.IBoundaryInputAdapter fakeUserCaptchaResponseAdapter() {

    return new IBoundariesAdapter.IBoundaryInputAdapter() {
      @Override
      public String getCaptchaResponseByUser() {
        return "responseClient";
      }

      @Override
      public String getHashOrDecrypteCaptchaResponse() {
        return "dddddddd";
      }

      @Override
      public CryptographicType getCryptographicType() {
        return CryptographicType.HASH;
      }

      @Override
      public String getCaptchaToken() {
        return "dddddd";
      }

      @Override
      public String getCaptchaTokenSeparator() {
        return "!%%!";
      }


    };
  }
  public static IBoundariesAdapter.IBoundaryInputAdapter fakeUserCaptchaResponseAdapterWithoutToken() {

    return new IBoundariesAdapter.IBoundaryInputAdapter() {
      @Override
      public String getCaptchaResponseByUser() {
        return "responseClient";
      }

      @Override
      public String getHashOrDecrypteCaptchaResponse() {
        return "dddddddd";
      }

      @Override
      public CryptographicType getCryptographicType() {
        return CryptographicType.HASH;
      }

      @Override
      public String getCaptchaToken() {
        return null;
      }

      @Override
      public String getCaptchaTokenSeparator() {
        return null;
      }


    };
  }
  public static IBoundariesAdapter.IBoundaryOutputAdapter userCaptchaResponseResultAdapterValid() {
    return new IBoundariesAdapter.IBoundaryOutputAdapter() {
      @Override
      public boolean getIsClientResponseValid() {
        return true;
      }
    };
  }
  public static IBoundariesAdapter.IBoundaryOutputAdapter userCaptchaResponseResultAdapterNotValid() {
    return new IBoundariesAdapter.IBoundaryOutputAdapter() {
      @Override
      public boolean getIsClientResponseValid() {
        return false;
      }
    };
  }
  public static IProfessional fakeProfessionalWithAccountNotConfirmed() {
    return new IProfessional() {
      @Override
      public Long getProfessionalId() {
        return 1l;
      }

      @Override
      public Long getClientId() {
        return 1l;
      }

      @Override
      public boolean isProfessionalAccountConfirmed() {
        return false;
      }

      @Override
      public boolean isProfessionalAccountValidateByAdmin() {
        return false;
      }

      @Override
      public LocalDateTime professionalAccountActivationAt() {
        return LocalDateTime.of(2022, 10,11, 23, 59, 59);
      }
    };
  }
  public static IProfessional fakeProfessionalWithAccountConfirmed() {

    return new IProfessional() {
      @Override
      public Long getProfessionalId() {
        return 1l;
      }

      @Override
      public Long getClientId() {
        return 1l;
      }

      @Override
      public boolean isProfessionalAccountConfirmed() {
        return true;
      }

      @Override
      public boolean isProfessionalAccountValidateByAdmin() {
        return true;
      }

      @Override
      public LocalDateTime professionalAccountActivationAt() {
        return LocalDateTime.of(2022, 10,11, 23, 59, 59);
      }
    };
  }
  public static com.ctoutweb.lalamiam.core.adapter.professionalInscriptionConfirmation.IBoundariesAdapter.IBoundaryInputAdapter fakeProfessionalConfirmationInformationAdapter() {
    return new com.ctoutweb.lalamiam.core.adapter.professionalInscriptionConfirmation.IBoundariesAdapter.IBoundaryInputAdapter() {
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
  }
  public static IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken fakeProfessionalConfirmationTokenAdapter() {
    return new IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken() {
      @Override
      public String getUrlHashToken() {
        return null;
      }

      @Override
      public String getEmailHashToken() {
        return null;
      }

      @Override
      public LocalDateTime getActivatedLimitTime() {
        return LocalDateTime.of(2022, 10,12, 23, 59, 59);
      }
    };
  }

  public static ProfessionalImpl fakeProfessionalWithAccountNotActive() {
    return new ProfessionalImpl(fakeProfessionalWithAccountNotConfirmed());
  }

  public static ProfessionalImpl fakeProfessionalWithAccountActive() {
    return new ProfessionalImpl(fakeProfessionalWithAccountConfirmed());
  }

  public static TokenToValidateImpl fakeTokenWithHashToken(String publicToken, String privateToken) {
    return new TokenToValidateImpl(publicToken, privateToken);
  }
  public static IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken fakeProfessionalConfirmationTokenAdapterWithConfirmDateValid() {
    return new IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken() {
      @Override
      public String getUrlHashToken() {
        return "urlHashToken";
      }

      @Override
      public String getEmailHashToken() {
        return "emailHashToken";
      }

      @Override
      public LocalDateTime getActivatedLimitTime() {
        return LocalDateTime.now().plusMonths(2);
      }
    };
  }

  public static IBoundaryInputAdapter fakeInputAdapter() {
    return new IBoundaryInputAdapter() {
      @Override
      public Long getProfessionalId() {
        return 1l;
      }
    };
  }

  public static IProfessionalDocument fakeProfessionalInscriptionDocument() {
    return new IProfessionalDocument() {
      @Override
      public Long getProfessionalId() {
        return 1l;
      }

      @Override
      public String getProfessionalEmail() {
        return "email@gmail.com";
      }

      @Override
      public String getProfessionalPhone() {
        return "phone number";
      }

      @Override
      public File getProfessionalFileStatus() {
        return new File("fake_path");
      }
    };
  }

  public static ProfessionalInscriptionDocumentImpl fakeProfessionalInscriptionDocumentImpl() {
    return new ProfessionalInscriptionDocumentImpl(fakeProfessionalInscriptionDocument());
  }

  public static ICaptchaStrategy fakeCaptchaTextStrategy(IMessageService messageService, ICryptographicService cryptographicService) {
    return new CaptchaTextStrategyImpl(messageService, cryptographicService, fakeCaptchaRepository(), fakeCaptchaConfiguration());
  }

  public static ICaptchaStrategy fakeCaptchaCalculStrategy(IMessageService messageService,ICryptographicService cryptographicService) {
     return new CaptchaCalculStrategyImpl(messageService, cryptographicService, fakeCaptchaRepository(), fakeCaptchaConfiguration());
  }

  public static ICaptchaStrategy fakeCaptchaImageStrategy(IMessageService messageService,ICryptographicService cryptographicService, ICaptchaRepository captchaRepository) {
    return new CaptchaImageStrategyImpl(messageService, cryptographicService, captchaRepository, fakeCaptchaConfiguration());
  }

  public static InputBoundaryAdapter getCreateImageInputBoundaryAdapter() {
    return new InputBoundaryAdapter() {
      @Override
      public String getTextToDraw() {
        return "bonjour";
      }
    };
  }

  public static OutputBoundary fakeOutputBoundary(BufferedImage image) {
    return new OutputBoundary(image);
  }

  public static BufferedImage fakeImage() {
    return new BufferedImage(20, 20, TYPE_INT_RGB);
  }

  public static com.ctoutweb.lalamiam.core.adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryInputAdapter getExecuteCaptchaStrategyInputBoundaryAdapter(IMessageService messageService, ICryptographicService cryptographicService) {
    CaptchaContextImpl captchaContext = fakeCaptchaContextCalculStrategy( messageService, cryptographicService);

    return new com.ctoutweb.lalamiam.core.adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryInputAdapter() {
      @Override
      public CaptchaContextImpl getCaptchaContext() {
        return captchaContext;
      }
    };
  }

  public static CaptchaContextImpl fakeCaptchaContextCalculStrategy(IMessageService messageService,ICryptographicService cryptographicService) {
    CaptchaContextImpl captchaContext = new CaptchaContextImpl();
    captchaContext.setCaptchaStrategy(fakeCaptchaCalculStrategy(messageService, cryptographicService));
    return captchaContext;
  }

  public static CaptchaContextImpl fakeCaptchaContextTextStrategy(IMessageService messageService, ICryptographicService cryptographicService) {
    CaptchaContextImpl captchaContext = new CaptchaContextImpl();
    captchaContext.setCaptchaStrategy(fakeCaptchaTextStrategy(messageService, cryptographicService));
    return captchaContext;
  }

  public static CaptchaContextImpl fakeCaptchaContextImageStrategy(IMessageService messageService, ICryptographicService cryptographicService, ICaptchaRepository captchaRepository) {
    CaptchaContextImpl captchaContext = new CaptchaContextImpl();
    captchaContext.setCaptchaStrategy(fakeCaptchaImageStrategy(messageService, cryptographicService, captchaRepository));
    return captchaContext;
  }

  public static IProfessionalInscription.ICreatedProfessionalAccount fakeProfessionalAccount() {
    return new IProfessionalInscription.ICreatedProfessionalAccount() {
      @Override
      public Long getAccountId() {
        return 3L;
      }
    };
  }

  public static ICaptcha.IAvailableCaptchaImage fakeAvailableCaptchaImage(String response, String path) {
    return new ICaptcha.IAvailableCaptchaImage() {
      @Override
      public String getCaptchaResponse() {
        return response;
      }

      @Override
      public String getImagePath() {
        return path;
      }
    };
  }
  public static ICryptography.ICryptographySaveResult fakeCryptographyResultWhenHash() {
    return new ICryptography.ICryptographySaveResult() {
      @Override
      public String getCryptographicText() {
        return "dmfdmfdmfmdfmldfjs";
      }

      @Override
      public CryptographicType getCryptographicType() {
        return CryptographicType.HASH;
      }

      @Override
      public Long getResponseId() {
        return 1L;
      }
    };
  }

  public static ICryptography.ICryptographySaveResult fakeCryptographyResultWhenEncryption() {
    return new ICryptography.ICryptographySaveResult() {
      @Override
      public String getCryptographicText() {
        return "dmfdmfdmfmdfmldfjs";
      }

      @Override
      public CryptographicType getCryptographicType() {
        return CryptographicType.ENCRYPTION;
      }

      @Override
      public Long getResponseId() {
        return 1L;
      }
    };
  }

  public static List<ICaptcha.IAvailableCaptchaImage> fakeCaptchaImages() {
    ICaptcha.IAvailableCaptchaImage image1 = new ICaptcha.IAvailableCaptchaImage() {
      @Override
      public String getCaptchaResponse() {
        return "reponse1";
      }

      @Override
      public String getImagePath() {
        return "path1";
      }
    };

    ICaptcha.IAvailableCaptchaImage image2 = new ICaptcha.IAvailableCaptchaImage() {
      @Override
      public String getCaptchaResponse() {
        return "reponse2";
      }

      @Override
      public String getImagePath() {
        return "path2";
      }
    };

    return List.of(image1, image2);
  }
}
