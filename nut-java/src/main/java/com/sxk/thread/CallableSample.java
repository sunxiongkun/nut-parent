package com.sxk.thread;

import com.sxk.util.StringUtils;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableSample {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    Callable<String> c1 = new Callable<String>() {
      @Override
      public String call() throws Exception {
        return StringUtils.randomStr(new Random());
      }
    };
    ExecutorService pool = Executors.newFixedThreadPool(8);
    Future<String> result = pool.submit(c1);
    System.out.println(result.get());
    pool.shutdown();
  }

}
