package com.sxk.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

  public static void main(String[] args) {
    ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(20);
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
        queue, new AbortPolicy());

    Runnable t = () -> {
      try {
        TimeUnit.SECONDS.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    try {
      for (int i = 1; i < 50; i++) {
        poolExecutor.execute(t);
        System.out.println(i + ":" + poolExecutor);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      poolExecutor.shutdown();
      while (!poolExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
        System.out.println(poolExecutor);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      poolExecutor.shutdownNow();
    }


  }

}
