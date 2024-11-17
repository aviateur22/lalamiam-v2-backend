package com.ctoutweb.lalmiam.core.factory;

import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.InputBoundaryAdapter;
import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.OutputBoundaryAdapter;
import com.ctoutweb.lalamiam.core.factory.CreateCaptchaImageFactory;
import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.createCaptchaImage.InputBoundary;
import com.ctoutweb.lalamiam.core.entity.createCaptchaImage.OutputBoundary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.ctoutweb.lalamiam.core.useCase.impl.CreateCaptchaImageUseCase;

import java.awt.image.BufferedImage;

public class CreateCaptchaImageFactoryTest {

  @Test
  public void getInput_should_return_CreateCaptchaImage_Input_model() {

    /**
     * given
     */
    InputBoundaryAdapter inputBoundaryAdapter = DataForTest.getCreateImageInputBoundaryAdapter();

    /**
     * when
     */
    CreateCaptchaImageUseCase.Input actualInput = CreateCaptchaImageFactory.getInput(inputBoundaryAdapter);
    InputBoundary actualInputInputBoundary = actualInput.getInputBoundary();

    /**
     * then
     */
    Assertions.assertEquals(inputBoundaryAdapter.getTextToDraw(), actualInputInputBoundary.getTextToDraw());
  }

  @Test
  public void getInputBoundary_should_return_InputBoundary_model() {
    /**
     * given
     */
    InputBoundaryAdapter inputBoundaryAdapter = DataForTest.getCreateImageInputBoundaryAdapter();

    /**
     * when
     */
    InputBoundary inputBoundary = CreateCaptchaImageFactory.getInputBoundary(inputBoundaryAdapter);
    String actualText = inputBoundary.getTextToDraw();
    String expectedText = inputBoundaryAdapter.getTextToDraw();

    /**
     * then
     */
    Assertions.assertEquals(expectedText, actualText);
  }

  @Test
  public void getOutput_should_return_CreateCaptchaImage_Output_model() {
    /**
     * given
     */
    InputBoundaryAdapter inputBoundaryAdapter = DataForTest.getCreateImageInputBoundaryAdapter();

    BufferedImage fakeImage = DataForTest.fakeImage();
    OutputBoundary outputBoundary = DataForTest.fakeOutputBoundary(fakeImage);

    /**
     * when
     */
    CreateCaptchaImageUseCase.Output actualOutput = CreateCaptchaImageFactory.getOutput(outputBoundary);
    OutputBoundaryAdapter actualOutputBoundaryAdapter = actualOutput.getOutputBoundaryAdapter();

    /**
     * then
     */
    Assertions.assertEquals(fakeImage, actualOutputBoundaryAdapter.getGeneratedImage());
  }
}
