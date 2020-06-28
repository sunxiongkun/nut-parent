package com.sxk.single.instance;

public class SingleDemo2 {

  private SingleDemo2() {
    System.out.println("init:" + this.hashCode());
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static class SingleInter {

    private final static SingleDemo2 INSTANCE = new SingleDemo2();
  }

  public static SingleDemo2 getInstance() {
    return SingleInter.INSTANCE;
  }


}
