package util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DateUtilTest {
  @Test
  public void should_add_5_days_with_time_23h59() {
    /**
     * given
     */
    int dayToAdd = 5;

    /**
     * then
     */
    LocalDateTime actualDate = DateUtil.addDayAtEndOfDay(dayToAdd);
    LocalDateTime expectedDate = LocalDateTime.now().plusDays(dayToAdd).with(LocalDateTime.MAX);

    /**
     * then
     */
    Assertions.assertEquals(expectedDate, actualDate);

  }
}
