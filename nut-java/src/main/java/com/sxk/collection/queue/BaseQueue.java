package com.sxk.collection.queue;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseQueue {

  public static void main(String[] args) {
    String[] array = {"a", "b", "c", "d", "e", "f", "h", "i", "j", "k", "l", "m", "n"};
    BlockingQueue<String> queue = new ArrayBlockingQueue<>(10, true);

    ExecutorService threadPool = Executors.newFixedThreadPool(10);

    threadPool.submit(new QueueProuder(queue, "1"));

    threadPool.submit(new QueueConsumer(queue, "1"));
    threadPool.submit(new QueueConsumer(queue, "2"));
    threadPool.submit(new QueueConsumer(queue, "3"));
    threadPool.submit(new QueueConsumer(queue, "4"));
    //threadPool.submit(new QueueConsumer(queue));

    try {
      Thread.sleep(600000);
      threadPool.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


  }

  public static BlockingQueue createArrayBlockQueue() {
    BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    return queue;
  }

  /**
   * 5秒钟消费一条数据
   */
  private static class QueueConsumer implements Runnable {

    private Queue<String> queue;
    private String name;

    public QueueConsumer(Queue<String> queue, String name) {
      this.queue = queue;
      Thread.currentThread().setName("queueConsumer-" + name);
    }

    @Override
    public void run() {
      try {
        while (true) {
          String result = this.queue.poll();
          System.out.println("consumer:" + result);
          Thread.sleep(5000);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 1秒钟生产一条数据
   */
  private static class QueueProuder implements Runnable {

    private Queue<String> queue;
    private String name;

    public QueueProuder(Queue<String> queue, String name) {
      this.queue = queue;
      Thread.currentThread().setName("queueProuder-" + name);
    }

    @Override
    public void run() {
      try {
        Random random = new Random();
        String str = null;
        while (true) {
          str = random.nextInt(1000) + "";
          System.out.println("prouder:" + str);
          this.queue.offer(str);
          Thread.sleep(1000);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


}
