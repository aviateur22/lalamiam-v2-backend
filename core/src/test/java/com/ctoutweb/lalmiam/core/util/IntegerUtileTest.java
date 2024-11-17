package com.ctoutweb.lalmiam.core.util;

import com.ctoutweb.lalamiam.core.util.IntegerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntegerUtileTest {
  @Test
  public void sould_generate_int_beween_5_and_10() {
    /**
     * given
     */
    int min = 5;
    int max = 10;
    for(int i = 0; i < 20; i++ )    {
      /**
       * when
       */
      int actualResponse = IntegerUtil.generateNumberBetween(min, max);

      /**
       *
       */
      Assertions.assertTrue(actualResponse>= min && actualResponse <= max);
    }
  }
}
