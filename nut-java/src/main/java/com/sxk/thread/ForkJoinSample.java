package com.sxk.thread;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.LongStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ForkJoinSample {

  public static void main(String[] args) throws Exception {
    System.out.println(UUID.randomUUID().toString());

    testTimeout();
  }

  private static void testTimeout() {
    ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
        queue, new AbortPolicy());
    log.info("start.....");
    for (int i = 0; i < 50; i++) {
      //testTimeout(poolExecutor);
      new Thread(() -> testTimeout(poolExecutor)).start();
    }
  }

  private static void testTimeout(ThreadPoolExecutor poolExecutor) {
    long now = System.currentTimeMillis();
    CompletableFuture<String> future = CompletableFuture
        .supplyAsync(() -> {
          try {
            testForkjoin();
            return "success";
          } catch (Exception e) {
            e.printStackTrace();
            return "error";
          }
        }, poolExecutor);
    //log.info("thread:{},submit task start...", Thread.currentThread().getId());
    try {
      future.get(1000, TimeUnit.MILLISECONDS);
      log.info("thread:{},submit task ,use time:{}", Thread.currentThread().getId(),
          System.currentTimeMillis() - now);
    } catch (TimeoutException e1) {
      log.error("thread:{},submit task ,use time:{},timeout:", Thread.currentThread().getId(),
          System.currentTimeMillis() - now);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private static void testForkjoin() {
    ForkJoinPool pool = new ForkJoinPool();
    ForkJoinTask<Long> task = new ForkTask(0, 1000000);
    Long totalNum = pool.invoke(task);
    System.out.println("1+...1000000=" + totalNum);
  }

  private static class ForkTask extends RecursiveTask<Long> {

    private static final int CAPACITY = 1000;
    private Integer start;
    private Integer end;

    public ForkTask(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }

    @Override
    protected Long compute() {
      if ((end - start) < CAPACITY) {
        try {
          TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
          return LongStream.range(start, end).sum();
        } catch (InterruptedException e) {
          log.error("forkjoin task error:", e);
          return 0L;
        }
      } else {
        //System.out.println("任务分解===========");
        // 将大任务分解成两个小任务
        int middle = (start + end) / 2;
        ForkTask left = new ForkTask(start, middle);
        ForkTask right = new ForkTask(middle, end);
        // 并行执行两个小任务
        left.fork();
        right.fork();
        // 把两个小任务累加的结果合并起来
        return left.join() + right.join();
      }
    }
  }

  private static class ForkAction extends RecursiveAction {

    @Override
    protected void compute() {

    }
  }

}
