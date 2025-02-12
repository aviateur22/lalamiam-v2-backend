//package com.ctoutweb.lalmiam.core.usecase.createCaptchaImage;
//
//import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.InputBoundaryAdapter;
//import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.OutputBoundaryAdapter;
//import common.DataForTest;
//import com.ctoutweb.lalamiam.core.factory.CreateCaptchaImageFactory;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import com.ctoutweb.lalamiam.core.useCase.impl.CreateCaptchaImageUseCase;
//
//import java.awt.image.BufferedImage;
//
//import static com.ctoutweb.lalamiam.core.constant.ApplicationConstant.CAPTCHA_IMAGE_HEIGHT;
//import static com.ctoutweb.lalamiam.core.constant.ApplicationConstant.CAPTCHA_IMAGE_WIDTH;
//
//public class CreateCaptchaImageUseCaseTest {
//
//  CreateCaptchaImageUseCase createImageUseCase;
//
//  @BeforeEach
//  public void init() {
//    createImageUseCase = new CreateCaptchaImageUseCase();
//  }
//
//  @Test
//  public void CreateCaptchaImageUseCase_should_return_new_image() {
//    /**
//     * given
//     */
//    InputBoundaryAdapter inputBoundaryAdapter = DataForTest.getCreateImageInputBoundaryAdapter();
//    CreateCaptchaImageUseCase.Input input = CreateCaptchaImageFactory.getInput(inputBoundaryAdapter);
//
//    /**
//     * when
//     */
//    CreateCaptchaImageUseCase.Output output = createImageUseCase.execute(input);
//    OutputBoundaryAdapter actualResponse = output.getOutputBoundaryAdapter();
//    BufferedImage actualImage = actualResponse.getGeneratedImage();
//
//    /**
//     * then
//     */
//    Assertions.assertNotNull(actualImage);
//    Assertions.assertEquals(actualImage.getHeight(), CAPTCHA_IMAGE_HEIGHT);
//    Assertions.assertEquals(actualImage.getWidth(), CAPTCHA_IMAGE_WIDTH);
//
//  }
//
//  @Test
//  public void createImage_should_create_image_with_text_bonjour() {
//    /**
//     * given
//     */
//    String textToDraw = "bonjour";
//    int height = 20;
//    int width = 20;
//
//    /**
//     * when
//     */
//     BufferedImage actualImage = createImageUseCase.generateImage(textToDraw, height, width);
//     BufferedImage expectedImage = createImageUseCase.generateImage(textToDraw, height, width);
//
//    /**
//     * then
//     */
//    Assertions.assertNotNull(actualImage);
//    Assertions.assertNotNull(expectedImage);
//
//    Assertions.assertEquals(expectedImage.getHeight(), actualImage.getHeight(), "La hauteur des images ne sont pas identique");
//    Assertions.assertEquals(expectedImage.getWidth(), actualImage.getWidth(), "La largeur des images ne sont pas identique");
//
//    for (int y = 0; y < actualImage.getHeight(); y++) {
//      for (int x = 0; x < actualImage.getWidth(); x++) {
//        Assertions.assertEquals(actualImage.getRGB(x, y), expectedImage.getRGB(x, y), "Les pixels ne sont pas identiques");
//      }
//    }
//  }
//}
