package com.sxk.hook;

public class OutOfMemoryHook {

  public static void main(String[] args) throws Exception {
    System.out.println("maxMem:" + Runtime.getRuntime().maxMemory() / 1024 / 1024);
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Execute Hook.....");
      }
    });
    thread.setName("HootTest");
    Runtime.getRuntime().addShutdownHook(thread);

    byte[] b = new byte[500 * 1024 * 1024];
    Thread.sleep(10000000);
  }
}
