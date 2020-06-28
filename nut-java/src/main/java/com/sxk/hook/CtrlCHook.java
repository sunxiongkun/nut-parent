package com.sxk.hook;

public class CtrlCHook {

  public static void main(String[] args) throws Exception {

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Execute Hook.....");
      }
    });
    thread.setName("HootTest");

    Runtime.getRuntime().addShutdownHook(thread);

    System.out.println("do something");
    System.out.println("press ctrl+c");
    Thread.sleep(10000000);
  }
}
