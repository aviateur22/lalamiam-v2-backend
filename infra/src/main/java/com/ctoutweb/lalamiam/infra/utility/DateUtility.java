package com.ctoutweb.lalamiam.infra.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    return time.toLocalDateTime().format(formatter);
  }

  /**
   * Convertion d'une ZoneDateTime UTC to ZonedDateTime with specific ZoneId
   * @param zoneId ZoneId - ZoneId souhaité
   * @param utcTime ZonedDateTime - Heure en UTC
   * @return ZonedDateTime
   */
  public static ZonedDateTime uctToZonedDateTime(ZoneId zoneId, ZonedDateTime utcTime) {
    if(utcTime == null)
      return null;

    return utcTime.withZoneSameInstant(zoneId);
  }

  /**
   * Convertion d'une LocalDateTime to ZonedDateTime with specific ZoneId
   * @param zoneId
   * @param localDateTime
   * @return ZonedDateTime
   */
  public static ZonedDateTime localDateTimeToZonedDateTime(ZoneId zoneId, LocalDateTime localDateTime) {
    return localDateTime.atZone(zoneId);
  }

}
