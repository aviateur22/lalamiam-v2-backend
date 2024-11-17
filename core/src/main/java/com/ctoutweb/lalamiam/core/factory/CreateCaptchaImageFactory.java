package com.ctoutweb.lalamiam.core.factory;

import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.InputBoundaryAdapter;
import com.ctoutweb.lalamiam.core.entity.createCaptchaImage.InputBoundary;
import com.ctoutweb.lalamiam.core.entity.createCaptchaImage.OutputBoundary;
import com.ctoutweb.lalamiam.core.useCase.impl.CreateCaptchaImageUseCase;

import java.awt.image.BufferedImage;

public class CreateCaptchaImageFactory {
  public static InputBoundary getInputBoundary(InputBoundaryAdapter inputBoundaryAdapter) {
    return new InputBoundary(inputBoundaryAdapter);
  }

  public static OutputBoundary getOutputBoundary(BufferedImage captchaImage) {
    return new OutputBoundary(captchaImage);
  }

  public static CreateCaptchaImageUseCase.Output getOutput(OutputBoundary outputBoundary) {
    return new CreateCaptchaImageUseCase.Output(outputBoundary);
  }

  public static CreateCaptchaImageUseCase.Input getInput(InputBoundaryAdapter inputBoundaryAdapter) {
    return new CreateCaptchaImageUseCase.Input(inputBoundaryAdapter);
  }
}
