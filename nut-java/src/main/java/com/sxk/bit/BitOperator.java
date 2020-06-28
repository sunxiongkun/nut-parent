package com.sxk.bit;

/**
 * & 按位与  a&1=0 偶数 a&1=1 奇数
 * | 按位或
 * ^ 按位异或
 * ~取反
 * << 左移  左移n位就是乘以2的n次方
 * >> 右移  右移n位就是除以2的n次方
 */
public class BitOperator {

  public static void main(String[] args) {
    int a = 2;
    int b = 4;
    System.out.println(a & b);
    System.out.println(a | b);
    System.out.println(a ^ b);
    System.out.println(~a);
    System.out.println(a << b);
    System.out.println(a >> b);
    System.out.println(a >>> b);

    //和2的次方-1做&运算，相当于取余
    int mod1 = 65 & 7;
    int mod2 = 65 % 8;
    System.out.println(mod1);
    System.out.println(mod2);


  }

  /**
   * 判断为偶数
   */
  public static boolean isEvenNum(Integer num) {
    return (num & 1) == 0;
  }

  /**
   * 判断为奇数
   */
  public static boolean isOddNum(Integer num) {
    return (num & 1) == 1;
  }
}
