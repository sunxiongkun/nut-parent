package com.sxk.thread;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeSample {


  public static void main(String[] args) {

  }

  public static Unsafe getUnsafe() {
    Field f = null;
    Unsafe unsafe = null;
    try {
      f = Unsafe.class.getDeclaredField("theUnsafe");
      f.setAccessible(true);
      unsafe = (Unsafe) f.get(null);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return unsafe;
  }

}
