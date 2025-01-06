package com.ctoutweb.lalamiam.infra.utility;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtility {
  /**
   * Formatte la date en date et HHmm
   * @param time - LocalDateTime
   * @return String
   */
  public static String toDateHour(ZonedDateTime time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return time.format(formatter);
  }
}
