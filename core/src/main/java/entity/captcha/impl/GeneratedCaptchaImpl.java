package entity.captcha.impl;

import entity.captcha.ICaptcha.IGeneratedCaptcha;

public record GeneratedCaptchaImpl(
        String captchaTitle,
        Long responseId,
        String captchaQuestion,
        boolean isQuestionToBeTransformedInImage

) implements IGeneratedCaptcha {
  public static IGeneratedCaptcha getGenerateCaptchaImpl(
          String captchaTitle,
          Long savedResponseId,
          String captchaQuestion,
          boolean isGeneratedQuestionToBeTransformInImage
  ) {
    return new GeneratedCaptchaImpl(
            captchaTitle,
            savedResponseId,
            captchaQuestion,
            isGeneratedQuestionToBeTransformInImage
    );
  }

  @Override
  public String getCaptchaTitle() {
    return captchaTitle;
  }

  @Override
  public Long getResponseId() {
    return responseId;
  }

  @Override
  public String getCaptchaQuestion() {
    return captchaQuestion;
  }

  @Override
  public boolean isQuestionToBeTransformedInImage() {
    return isQuestionToBeTransformedInImage;
  }
}
