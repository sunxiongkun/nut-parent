package com.sxk.hook;

public class UnCatchExceptionHook {

  public static void main(String[] args) throws Exception{
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Execute Hook.....");
      }
    }));
    //运行时异常
    int i = 1 / 0;
    Thread.sleep(10000000);
  }
}
