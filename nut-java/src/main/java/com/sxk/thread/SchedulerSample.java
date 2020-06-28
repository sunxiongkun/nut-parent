package com.sxk.thread;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SchedulerSample {

  public static void main(String[] args) {
    ScheduledThreadPoolExecutor scheduledThreadPool = new ScheduledThreadPoolExecutor(1);

    Runnable task = () -> {
      System.out.println(System.currentTimeMillis());
    };

    ScheduledFuture<?> future = scheduledThreadPool
        .scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);



  }

}
