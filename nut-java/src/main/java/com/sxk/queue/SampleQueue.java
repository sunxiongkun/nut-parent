package com.sxk.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sxk
 * @date 2021/4/29 10:43 上午
 */
@Slf4j
public class SampleQueue {

  public static void main(String[] args) {
    final SampleQueue queue = new SampleQueue();
    Runnable r = () -> {
      for (int i = 0; i < 100; i++) {
        queue.push(String.valueOf(i));
      }
    };
    Runnable r2 = () -> {
      for (int i = 0; i < 100; i++) {
        System.out.println(queue.pop());
      }
    };
    final Thread t1 = new Thread(r);
    t1.setName("push pool");
    final Thread t2 = new Thread(r2);
    t2.setName("pop pool");
    t1.start();
    t2.start();


  }

  private Lock lock = new ReentrantLock();

  private Condition notEmpty = lock.newCondition();

  private Condition notFull = lock.newCondition();

  private List<String> list = new ArrayList<>();

  public boolean push(String str) {
    try {
      lock.lock();
      while (list.size() == 10) {
        //等待的队列不满
        notFull.await();
      }
      list.add(str);
      //通知队列不为空
      notEmpty.signal();
    } catch (Exception e) {
      log.error("lock error:", e);
    } finally {
      lock.unlock();
    }
    return true;
  }

  public String pop() {
    try {
      lock.lock();
      while (list.isEmpty()) {
        //等待的队列不为空
        notEmpty.await();
      }
      final String res = list.get(0);
      list.remove(0);
      //通知可以入队
      notFull.signal();
      return res;
    } catch (Exception e) {
      log.error("lock error:", e);
    } finally {
      lock.unlock();
    }
    return null;
  }
}
