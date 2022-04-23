package com.sxk.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPoolExecutorTest {

  public static void main(String[] args) {

    //testThreadQueue();

    testTimeout();

  }

  public static void testThreadQueue() {
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

  public static void testTimeout() {
    ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(20);
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
        queue, new AbortPolicy());

    Callable<String> t = () -> {
      try {
        Thread.sleep(110000);
        System.out.println("thread:" + Thread.currentThread().getId() + "sleep start...");
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2));
        System.out.println("thread:" + Thread.currentThread().getId() + "sleep continue...");
        return "success";
      } catch (InterruptedException e) {
        e.printStackTrace();
        return "error";
      }
    };

    for (int i = 1; i < 50; i++) {
      try {
        Future<String> feature = poolExecutor.submit(t);
        String res = feature.get(1, TimeUnit.SECONDS);
        System.out.println(i + ":" + res);
      } catch (TimeoutException e1) {
        log.error("num:{},timeout", i, e1);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    poolExecutor.shutdown();


  }

}
