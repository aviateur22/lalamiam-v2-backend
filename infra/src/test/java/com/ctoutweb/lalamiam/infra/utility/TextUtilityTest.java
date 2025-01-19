package com.ctoutweb.lalamiam.infra.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TextUtilityTest {

  @Test
  public void replaceWordInText_should_return_updated_text() {
    /**
     * given
     */
    String text1 = "bonjour !%!user!%! comment vas tu?";
    Map<String, String> textToUpdate1 = new HashMap<>();
    textToUpdate1.put("!%!user!%!", "bob");

    Map<String, String> textToUpdate2= new HashMap<>();

    /**
     * when
     */
    String actualText1 = TextUtility.replaceWordInText(text1, textToUpdate1);
    String actualText2 = TextUtility.replaceWordInText(text1, textToUpdate2);

    /**
     * then
     */
    String expectedResponse1 = "bonjour bob comment vas tu?";
    String expectedResponse2 = "bonjour !%!user!%! comment vas tu?";
    Assertions.assertEquals(expectedResponse1, actualText1);
    Assertions.assertEquals(expectedResponse2, actualText2);
  }
}
