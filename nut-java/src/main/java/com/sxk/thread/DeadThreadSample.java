package com.sxk.thread;

public class DeadThreadSample {

  public static void main(String[] args) {
    Object o1 = new Object();
    Object o2 = new Object();
    Runnable t1 = () -> {
      Thread.currentThread().setName("DeadThread01");
      System.out.println("thread one start...");
      synchronized (o1) {
        System.out.println("get o1 lock");
        try {
          Thread.sleep(1000);
          synchronized (o2) {
            System.out.println("get o2 lock");
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println("thread two end...");
    };

    Runnable t2 = () -> {
      Thread.currentThread().setName("DeadThread02");
      System.out.println("thread two start...");
      synchronized (o2) {
        System.out.println("get o2 lock");
        try {
          Thread.sleep(1000);
          synchronized (o1) {
            System.out.println("get o1 lock");
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println("thread two end...");
    };

    new Thread(t1).start();
    new Thread(t2).start();
    System.out.println("finished");
  }

}
