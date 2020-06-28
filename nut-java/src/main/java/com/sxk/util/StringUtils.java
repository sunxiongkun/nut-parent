package com.sxk.util;

import java.util.Random;

public class StringUtils {

  static char[] charArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();

  public static String randomStr(Random r) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 1; i++) {
      sb.append(charArray[r.nextInt(26)]);
    }
    return sb.toString();
  }
}
