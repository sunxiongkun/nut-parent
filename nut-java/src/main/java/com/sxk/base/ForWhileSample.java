package com.sxk.base;

import java.util.Random;

/**
 * 使用break的场景有两种：一、switch语句中。二、循环语句。
 */
public class ForWhileSample {

  public static void main(String[] args) {
    testWhile();
    testFor();
  }

  static void testWhile() {
    Random r = new Random();
    loop:
    while (true) {
      int num = r.nextInt(100);
      switch (num) {
        case 1:
          System.out.println("first break");
          break;
        case 5:
          System.out.println("break loop:" + num);
          break loop;
        default:
          System.out.println(num);
      }
    }
  }

  static void testFor() {
    outerLoop:
    for (int i = 0; i < 17; i++) {
      for (int j = 0; j < 16; j++) {
        System.out.println("i:" + i + "j:" + j);
        if (j == 10) {
          break outerLoop; // 跳出外层循环
        }
      }
    }
  }

}
