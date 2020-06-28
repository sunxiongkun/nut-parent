package com.sxk.single.instance;

public class SingleDemo1 {

  /**
   * volatile 有没有结果都一样
   */

  private static SingleDemo1 instance = null;
  //private volatile static SingleDemo1 instance = null;

  private SingleDemo1() {
    System.out.println("init:" + this.hashCode());
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static SingleDemo1 getInstance() {
    if (instance == null) {
      synchronized (SingleDemo1.class) {
        if (instance == null) {
          instance = new SingleDemo1();
        }
      }
    }
    return instance;
  }


}
