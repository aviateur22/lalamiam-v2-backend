package util;

import common.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FunctionTest {
  @Test
  public void hasNoDigits_should_return_true_if_no_digit() {
    /**
     * given
     */
    String text = "addffvgdf";

    /**
     * when
     */
    boolean actualResponse = Function.hasNoDigits(text);

    /**
     * then
     */
    Assertions.assertTrue(actualResponse);
  }

  @Test
  public void hasNoDigits_should_return_false_if_1_digit() {
    /**
     * given
     */
    String text = "a0fvgdf";

    /**
     * when
     */
    boolean actualResponse = Function.hasNoDigits(text);

    /**
     * then
     */
    Assertions.assertFalse(actualResponse);
  }

  @Test
  public void hasNoDigits_should_return_false_if_only_digit() {
    /**
     * given
     */
    String text = "01236";

    /**
     * when
     */
    boolean actualResponse = Function.hasNoDigits(text);


    /**
     * then
     */
    Assertions.assertFalse(actualResponse);
  }
}
