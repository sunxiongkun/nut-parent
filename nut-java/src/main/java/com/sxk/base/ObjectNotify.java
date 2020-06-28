package com.sxk.base;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ObjectNotify {

  private static char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

  public static void main(String[] args) throws Exception {
    //changePrint();
    //changePrintWithLock();
    //testNotify();
    changeRunSemaphore();
    //testLockCondition();

  }

  /**
   * 线程交替运行 公平锁
   */
  private static void changeRunSemaphore() throws Exception {
    Semaphore semaphore = new Semaphore(1, true);

    Runnable r1 = () -> {
      while (true) {
        try {
          semaphore.acquire();
          Thread.sleep(100);
          System.out.println(Thread.currentThread().getName() + "-running");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          semaphore.release();
        }
      }
    };

    Thread t1 = new Thread(r1);
    Thread t2 = new Thread(r1);
    Thread t3 = new Thread(r1);
    t1.start();
    t2.start();
    t3.start();


  }

  private static void changeRunQueue() throws Exception {

  }


  /**
   * 写两个线程，一个线程打印1~52，另一个线程打印A~Z，打印顺序是12A34B....5152Z
   */
  private static void changePrint() throws Exception {
    Object o = new Object();

    Runnable r1 = () -> {
      for (int i = 1; i < 53; i++) {
        synchronized (o) {
          try {
            System.out.print(i);
            if (i % 2 == 0) {
              o.notify(); //唤醒其他线程
              o.wait(); //等待
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };

    Runnable r2 = () -> {
      for (int i = 0; i < chars.length; i++) {
        synchronized (o) {
          try {
            System.out.println(chars[i]);
            o.notify(); //唤醒其他线程
            if (i != chars.length - 1) {
              o.wait();
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    };

    Thread t1 = new Thread(r1);
    Thread t2 = new Thread(r2);
    t1.start();
    t2.start();

  }

  /**
   * 写两个线程，一个线程打印1~52，另一个线程打印A~Z，打印顺序是12A34B....5152Z
   */
  private static void changePrintWithLock() throws Exception {
    Lock lock = new ReentrantLock();

    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();

    Runnable r1 = () -> {
      for (int i = 1; i < 53; i++) {
        lock.lock();
        try {
          System.out.print(i);
          if (i % 2 == 0 && i != 52) {
            c1.await();
            c2.signal();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }
      }
    };

    Runnable r2 = () -> {
      for (int i = 0; i < chars.length; i++) {
        lock.lock();
        try {
          System.out.println(chars[i]);
          c2.await();
          c1.signal();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }
      }
    };

    Thread t1 = new Thread(r1);
    Thread t2 = new Thread(r2);
    t1.start();
    Thread.sleep(100);
    t2.start();
  }


  private static void testNotify() {
    Object o = new Object();

    Runnable r1 = () -> {
      synchronized (o) {
        System.out.println("wait start...");
        try {
          o.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("wait end...");
      }
    };

    Runnable r2 = () -> {
      synchronized (o) {
        System.out.println("notify start...");
        o.notifyAll();
        System.out.println("notify end...");
      }
    };
    Thread t1 = new Thread(r1);
    t1.setName("waitOne");
    t1.setPriority(Thread.MIN_PRIORITY);
    Thread t2 = new Thread(r1);
    t2.setPriority(Thread.MAX_PRIORITY);
    t2.setName("waitTwo");
    Thread t3 = new Thread(r2);
    t3.setName("notify");
    t1.start();
    t2.start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t3.start();

    System.out.println("end");
  }

  private static void testLockCondition() throws Exception {
    BoundedBuffer boundedBuffer = new BoundedBuffer();
    Runnable r1 = () -> {
      try {
        for (int i = 1; i < 110; i++) {
          System.out.println("put:" + i);
          boundedBuffer.put(i);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    Runnable r2 = () -> {
      try {
        for (int i = 1; i < 110; i++) {
          Thread.sleep(100);
          System.out.println("take:" + boundedBuffer.take());
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    Thread t1 = new Thread(r1);
    Thread t2 = new Thread(r2);
    t1.start();
    Thread.sleep(1000);
    t2.start();
  }

}


class BoundedBuffer {

  final Lock lock = new ReentrantLock();
  final Condition notFull = lock.newCondition();
  final Condition notEmpty = lock.newCondition();

  final Object[] items = new Object[100];
  int putptr, takeptr, count;

  public void put(Object x) throws InterruptedException {
    lock.lock();
    try {
      while (count == items.length) {
        notFull.await();
      }
      items[putptr] = x;
      if (++putptr == items.length) {
        putptr = 0;
      }
      ++count;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

  public Object take() throws InterruptedException {
    lock.lock();
    try {
      while (count == 0) {
        notEmpty.await();
      }
      Object x = items[takeptr];
      if (++takeptr == items.length) {
        takeptr = 0;
      }
      --count;
      notFull.signal();
      return x;
    } finally {
      lock.unlock();
    }
  }
}
