package com.sxk.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author: sxk Date: 2018/6/27 Description:
 */
public class LocalDateUtils {

  public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss");
  public static final DateTimeFormatter DAY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  public static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");
  public static final ZoneId SYSTEM_ZONE_ID = ZoneId.systemDefault();

  /**
   * 获取当天日期
   */

  public static String now(String format) {
    return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
  }

  /**
   * 获取当前月份的第一天
   */
  public static LocalDate getFirstOfMonth(LocalDate date) {
    return date.with(TemporalAdjusters.firstDayOfMonth());
  }

  /**
   * 获取当前月份的最后一天
   */
  public static LocalDate getEndOfMonth(LocalDate date) {
    return date.with(TemporalAdjusters.lastDayOfMonth());
  }

  /**
   * 获取当前月份的最后一天
   */
  public static LocalDateTime getEndOfMonth(LocalDateTime date) {
    return date.with(TemporalAdjusters.lastDayOfMonth());
  }

  /**
   * Date 转换成 LocalDateTime
   */
  public static LocalDateTime dateToLocalDateTime(Date date) {
    Instant instant = date.toInstant();
    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, SYSTEM_ZONE_ID);
    return localDateTime;
  }

  /**
   * 格式化为 yyyyMM
   */
  public static String formatYearMonth(LocalDate date) {
    return format(date, MONTH_FORMAT);
  }

  /**
   * 格式化为 yyyy-MM-dd
   */
  public static String formatYearMonthDay(LocalDate date) {
    return format(date, DAY_FORMAT);
  }

  /**
   * 格式化
   */
  public static String format(LocalDate date, DateTimeFormatter formatter) {
    return date.format(formatter);
  }

  public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
    return dateTime.format(formatter);
  }

  public static Date localDate2Date(LocalDate localDate) {
    Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date localDateTime2Date(LocalDateTime localDateTime) {
    ZonedDateTime zdt = localDateTime.atZone(SYSTEM_ZONE_ID);
    return Date.from(zdt.toInstant());
  }

  /**
   * timestamp -> LocalDataTime.
   */
  public static LocalDateTime timestampToLocalDateTime(long timestamp) {
    LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(SYSTEM_ZONE_ID)
        .toLocalDateTime();
    return localDateTime;
  }

  /**
   * LocalDataTime -> timestamp.
   */
  public static long localDateTimeToTimestamp(LocalDateTime localDateTime) {
    return localDateTime.atZone(SYSTEM_ZONE_ID).toInstant().toEpochMilli();
  }

  /**
   * LocalData -> timestamp.
   */
  public static long localDateToTimestamp(LocalDate localDate) {
    return localDate.atStartOfDay(SYSTEM_ZONE_ID).toInstant().toEpochMilli();
  }

  /**
   * timestamp -> LocalData.
   */
  public static LocalDate timestampToLocalDate(long timestamp) {
    return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault())
        .toLocalDate();
  }

  /**
   * String -> LocalDataTime.
   */
  public static LocalDateTime stringToLocalDateTime(String dateStr, DateTimeFormatter dtf) {
    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dtf);
    return localDateTime;
  }

  /**
   * String -> LocalData.
   */
  public static LocalDate stringToLocalDate(String dateStr) {
    return stringToLocalDate(dateStr, DAY_FORMAT);
  }

  /**
   * String -> LocalData.
   */
  public static LocalDate stringToLocalDate(String dateStr, DateTimeFormatter dtf) {
    LocalDate localDate = LocalDate.parse(dateStr, dtf);
    return localDate;
  }

  /**
   * 获取两个LocalDateTime月份差的绝对值.
   */
  public static long getDiffMonth(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
    return Math.abs(fromDateTime.toLocalDate().until(toDateTime.toLocalDate(), ChronoUnit.MONTHS));
  }

  /**
   * 获取两个LocalDateTime天数差的绝对值.
   */
  public static long getDiffDays(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
    return Math
        .abs(toDateTime.toLocalDate().toEpochDay() - fromDateTime.toLocalDate().toEpochDay());
  }

  /**
   * 获取两个LocalDate天数差的绝对值.
   */
  public static long getDiffDays(LocalDate fromDate, LocalDate toDate) {
    return Math.abs(toDate.toEpochDay() - fromDate.toEpochDay());
  }


  /**
   * 格式 yyyy-MM-dd
   *
   * @return unix time (13位)
   */

  public static Long convertDateToLong(String dateStr) {
    try {
      return localDateToTimestamp(stringToLocalDate(dateStr));
    } catch (Exception e) {
      //e.printStackTrace();
      return System.currentTimeMillis();
    }
  }

  /**
   * 格式 yyyy-MM-dd HH:mm:ss
   *
   * @return unix time (13位)
   */
  public static Long convertTimeToLong(String time) {
    try {
      LocalDateTime parse = LocalDateTime.parse(time, DATE_TIME_FORMAT);
      return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    } catch (Exception e) {
      //e.printStackTrace();
      return System.currentTimeMillis();
    }
  }


  /**
   * 获取事件当天发生毫秒数
   *
   * @param localDateTime 事件发生时间
   * @return 事件发生当天毫秒数（String类型）
   */
  public static String getEventOccurMillis(LocalDateTime localDateTime) {
    long millisOfBegin = localDateTime.toLocalDate().atStartOfDay(SYSTEM_ZONE_ID)
        .toInstant().toEpochMilli();
    long millis = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    return String.valueOf(millis - millisOfBegin);
  }

}
