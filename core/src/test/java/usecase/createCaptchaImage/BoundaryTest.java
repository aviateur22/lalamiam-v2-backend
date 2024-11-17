package usecase.createCaptchaImage;

import adapter.createImage.boundary.InputBoundaryAdapter;
import adapter.createImage.boundary.OutputBoundaryAdapter;
import common.DataForTest;
import entity.createCaptchaImage.InputBoundary;
import entity.createCaptchaImage.OutputBoundary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import useCase.impl.CreateCaptchaImageUseCase;

import java.awt.image.BufferedImage;

public class BoundaryTest {
  @Test
  public void should_validate_input() {
    /**
     * given
     */
    InputBoundaryAdapter inputBoundaryAdapter = DataForTest.getCreateImageInputBoundaryAdapter();

    /**
     * when
     */
    CreateCaptchaImageUseCase.Input input = new CreateCaptchaImageUseCase.Input(inputBoundaryAdapter);
    InputBoundary inputBoundary = input.getInputBoundary();

    /**
     * then
     */
    Assertions.assertEquals(inputBoundary.getTextToDraw(), "bonjour");
  }

  @Test
  public void should_validate_output() {
    /**
     * given
     */
    BufferedImage fakeImage = DataForTest.fakeImage();
    OutputBoundary outputBoundary = DataForTest.fakeOutputBoundary(fakeImage);

    /**
     * when
     */
    CreateCaptchaImageUseCase.Output actualOutput = new CreateCaptchaImageUseCase.Output(outputBoundary);
    OutputBoundaryAdapter actualBoundaryAdapter = actualOutput.getOutputBoundaryAdapter();
    BufferedImage actualImage = actualBoundaryAdapter.getGeneratedImage();
    int actualWidth = actualImage.getWidth();
    int actualHeight = actualImage.getHeight();

    /**
     * then
     */
    Assertions.assertNotNull(actualImage);
    Assertions.assertEquals(fakeImage.getWidth(), actualWidth, "Les width ne correspondent pas");
    Assertions.assertEquals(fakeImage.getHeight(), actualHeight, "Les height ne correspondent pas");


  }
}
