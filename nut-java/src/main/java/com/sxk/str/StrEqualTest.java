package com.sxk.str;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrEqualTest {

  public static void main(String[] args) {
    String s1 = "helloWorld";
    String s2 = new String("helloWorld");
    String s3 = "hello";
    String s4 = "World";
    String s5 = "hello" + "World";
    String s6 = s3 + s4;
    System.out.println(s1 == s2);
    System.out.println(s1 == s5);
    System.out.println(s1 == s6);
    System.out.println(s1.intern());
    System.out.println(s5.intern());

    log.info("aa:{}", "${jndi:'open /Applications/Typora.app'}");


  }

}
