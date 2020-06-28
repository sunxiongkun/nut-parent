package com.sxk.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

  private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8',
      '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  public static String md5(String str) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      // 获得密文
      byte[] bytes = md5.digest(str.getBytes(StandardCharsets.UTF_8));
      //System.out.println(bytes.length);
      // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
      // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
      char[] chars = new char[32];
      for (int i = 0; i < chars.length; i += 2) {
        byte b = bytes[i / 2];
        chars[i] = HEX_CHARS[b >>> 4 & 15];
        chars[i + 1] = HEX_CHARS[b & 15];
      }

      return new BigInteger(1, bytes).toString(16);
      //return new String(chars);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) {
//    int a = 077;
//    int b = 0xff;
//    int c = 15;
//    int d = 0b00000011;
//    System.out.println(Integer.toOctalString(c));
//    System.out.println(Integer.toHexString(c));
//    System.out.println(Integer.toString(c, 16));
//    System.out.println(String.format("%d,%d,%d,%d", a, b, c, d));
    //System.out.println(md5("123456"));
    String s = "{\"realname\":孙龙,\"phone\":18710060850,\"shenfenzheng\":360102198504083812,\"area\":3,\"shop\":52,\"pickdate\":2020-02-20,\"picktime\":11:00-13:00,\"shuliang\":5,\"pid\":1}";
    //[1,"71151","2020-02-19 19:30:43","319637ba64f1610ca3ae9eef65e07a06"]
    System.out.println(md5("71151"));
    System.out.println("319637ba64f1610ca3ae9eef65e07a06");
    System.out.println(md5("72940"));
    // [1,"72940","2020-02-19 19:37:51","30f662d1af02dd1e0a836fcc7c1b8c02"]
    System.out.println("30f662d1af02dd1e0a836fcc7c1b8c02");
    // [1,"74060","2020-02-19 19:41:30","77895fd343cef95f0616413326ae920c"]
    System.out.println("77895fd343cef95f0616413326ae920c");
  }

}
