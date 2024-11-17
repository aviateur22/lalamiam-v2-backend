package com.ctoutweb.lalamiam.core.util;

import java.time.LocalDateTime;
public class DateUtil {
  /**
   * Renvoi une nouvelle LocalDateTime a 23H59h59
   * @return LocalDateTime
   */
  public static LocalDateTime addDayAtEndOfDay(int dayToAdd) {
    return LocalDateTime.now().plusDays(dayToAdd).with(LocalDateTime.MAX);
  }
}
