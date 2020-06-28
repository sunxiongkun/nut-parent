package com.sxk.single.instance;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadInstance {

  private final static ThreadLocal<ThreadInstance> LOCAL = new ThreadLocal();

  private ThreadInstance() {

  }

  public static ThreadInstance getInstance() {
    ThreadInstance instance = LOCAL.get();
    if (instance == null) {
      instance = new ThreadInstance();
      LOCAL.set(instance);
    }
    return instance;
  }

  public static void main(String[] args) {

    Runnable task = () -> {
      System.out.println(
          Thread.currentThread().toString() + ":" + ThreadInstance.getInstance().hashCode());
    };

    ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 20, 6, TimeUnit.SECONDS,
        new ArrayBlockingQueue<Runnable>(100),
        new ThreadFactoryBuilder().setNameFormat("single-pool-%d").build());

    IntStream.range(0, 100).parallel().forEach(i -> {
      pool.submit(task);
    });
    pool.shutdown();

  }

}
