package com.sxk.hook;

public class NormalExitHook {


  public static Thread createThread() {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Execute Hook.....");
      }
    });
    thread.setName("HootTest");
    return thread;
  }

  public static void main(String[] args) {
    System.out.println("maxMem:" + Runtime.getRuntime().maxMemory() / 1024 / 1024 / 1024);
    System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
    System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);

    Runtime.getRuntime().addShutdownHook(createThread());
    System.out.println("do something");
  }
}
