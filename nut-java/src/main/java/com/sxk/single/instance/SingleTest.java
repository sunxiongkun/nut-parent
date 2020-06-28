package com.sxk.single.instance;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SingleTest {

  public static void main(String[] args) throws Exception {
    testDoubleCheck();
    //testInnerClass();
  }

  public static void testDoubleCheck() throws Exception {
    ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);
    System.out.println("start...");
    ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 16, 60,
        TimeUnit.MINUTES, queue, new ThreadFactoryBuilder().setNameFormat("singleDemo-%d").build());

    Runnable task = () -> {
      System.out.println(SingleDemo1.getInstance().hashCode() + " " + pool.toString());
    };
    long start = System.currentTimeMillis();
    IntStream.range(0, 100).forEach(index ->
        pool.submit(task));

    pool.shutdown();
    while (!pool.awaitTermination(60, TimeUnit.MINUTES)) {
      System.out.println("wait...");
    }
    System.out.println("耗时:" + (System.currentTimeMillis() - start));

  }

  public static void testInnerClass() throws Exception {

    ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);
    System.out.println("start...");
    ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 16, 60,
        TimeUnit.MINUTES, queue, new ThreadFactoryBuilder().setNameFormat("singleDemo-%d").build());

    Runnable task = () -> {
      System.out.println(SingleDemo2.getInstance().hashCode() + " " + pool.toString());
    };

    long start = System.currentTimeMillis();

    IntStream.range(0, 100).forEach(index ->
        pool.submit(task));

    pool.shutdown();
    while (!pool.awaitTermination(60, TimeUnit.MINUTES)) {
      System.out.println("wait...");
    }

    System.out.println("耗时:" + (System.currentTimeMillis() - start));


  }
}
