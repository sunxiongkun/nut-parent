package com.sxk.thread;

import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSample {

  public static void main(String[] args) throws Exception {
    String str = "2019-12-17";
    System.out.println(UUID.randomUUID().toString());

    ForkJoinPool pool = new ForkJoinPool();
    ForkJoinTask<Long> task = new ForkTask(0, 1000000);

    Long totalNum = pool.invoke(task);

    System.out.println("1+...1000000=" + totalNum);
  }

  private static  class ForkTask extends RecursiveTask<Long> {

    private static final int CAPACITY = 100000;
    private Integer start;
    private Integer end;

    public ForkTask(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }

    @Override
    protected Long compute() {
      if ((end - start) < CAPACITY) {
        return LongStream.range(start, end).sum();
      } else {
        System.out.println("任务分解===========");
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
