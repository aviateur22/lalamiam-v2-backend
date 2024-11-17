package com.ctoutweb.lalamiam.core.useCase.impl;

import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.OutputBoundaryAdapter;
import com.ctoutweb.lalamiam.core.constant.ApplicationConstant;
import com.ctoutweb.lalamiam.core.entity.createCaptchaImage.InputBoundary;
import com.ctoutweb.lalamiam.core.entity.createCaptchaImage.OutputBoundary;
import com.ctoutweb.lalamiam.core.factory.CreateCaptchaImageFactory;
import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.InputBoundaryAdapter;
import com.ctoutweb.lalamiam.core.useCase.UseCase;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CreateCaptchaImageUseCase implements UseCase<CreateCaptchaImageUseCase.Input, CreateCaptchaImageUseCase.Output> {

  @Override
  public Output execute(Input input) {
    InputBoundary inputBoundary = input.getInputBoundary();
    String textToDraw = inputBoundary.getTextToDraw();

    // Géneration d'une image
    BufferedImage captchaImage = this.generateImage(textToDraw, ApplicationConstant.CAPTCHA_IMAGE_HEIGHT, ApplicationConstant.CAPTCHA_IMAGE_WIDTH);

    // OutputBoundary UseCase
    OutputBoundary outputBoundary = CreateCaptchaImageFactory.getOutputBoundary(captchaImage);

    return CreateCaptchaImageFactory.getOutput(outputBoundary);
  }
  public BufferedImage generateImage(String textToDraw, int height, int width) {
    // BufferedImage object
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    // Graphics2D
    Graphics2D g2d = image.createGraphics();

    // Rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // Background color
    g2d.setColor(Color.lightGray);
    g2d.fillRect(0, 0, width, height);

    // Font and color
    g2d.setFont(new Font("Serif", Font.BOLD, 20));
    g2d.setColor(Color.BLACK);

    // Get the font metrics for calculating the position
    FontMetrics fm = g2d.getFontMetrics();
    int x = (width - fm.stringWidth(textToDraw)) / 2;
    int y = ((height - fm.getHeight()) / 2) + fm.getAscent();

    // Rendu du text
    g2d.drawString(textToDraw, x, y);

    // Supp Graphics2D
    g2d.dispose();

    return image;
  }

  public static class Input implements UseCase.Input {
    private final InputBoundaryAdapter inputBoundaryAdapter;

    public Input(InputBoundaryAdapter inputBoundaryAdapter) {
      this.inputBoundaryAdapter = inputBoundaryAdapter;
    }


    public InputBoundary getInputBoundary() {
      return CreateCaptchaImageFactory.getInputBoundary(inputBoundaryAdapter);
    }
  }

  public static class Output implements UseCase.Output {
    private final OutputBoundary outputBoundary;

    public Output(OutputBoundary outputBoundary) {
      this.outputBoundary = outputBoundary;
    }

    public OutputBoundaryAdapter getOutputBoundaryAdapter() {
      return outputBoundary;
    }
  }

}
