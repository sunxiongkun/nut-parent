package com.sxk.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sxk
 * @date 2021/4/12 2:46 下午
 */
@Slf4j
public class FixThreadSample {

  public static void main(String[] args) throws InterruptedException {
    int coreSize = 10;
    int maxSize = 100;
    BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
    final ThreadPoolExecutor pool = new ThreadPoolExecutor(coreSize, maxSize, 60,
        TimeUnit.SECONDS, queue);
    Runnable r = () -> {
      try {
        TimeUnit.HOURS.sleep(1);
      } catch (InterruptedException e) {
        log.error("runnable error...");
      }
    };
    for (int i = 0; i < 100; i++) {
      pool.submit(r);
      System.out.println("run..." + pool);
    }
    pool.shutdown();
    while (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
      System.out.println("end..." + pool);
    }

  }

}
