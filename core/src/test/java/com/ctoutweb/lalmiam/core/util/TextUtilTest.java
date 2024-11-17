package com.ctoutweb.lalmiam.core.util;

import com.ctoutweb.lalamiam.core.util.TextUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextUtilTest {
  @Test
  public void generateTextToLowerCase_should_return_string_length_9() {
    /**
     * given
     */
    int textLength = 9;

    /**
     * when
     */
    String actualText = TextUtil.generateTextToLowerCase(textLength);

    /**
     * then
     */
    Assertions.assertEquals(textLength, actualText.length());
  }

  @Test
  public void generateText_should_return_string_length_19() {
    /**
     * given
     */
    int textLength = 19;

    /**
     * when
     */
    String actualText = TextUtil.generateText(textLength);

    /**
     * then
     */
    Assertions.assertEquals(textLength, actualText.length());
  }
}
