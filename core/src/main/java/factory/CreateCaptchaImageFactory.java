package factory;

import adapter.createImage.boundary.InputBoundaryAdapter;
import entity.createCaptchaImage.InputBoundary;
import entity.createCaptchaImage.OutputBoundary;
import useCase.impl.CreateCaptchaImageUseCase;

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
