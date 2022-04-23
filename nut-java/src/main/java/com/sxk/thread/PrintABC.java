package com.sxk.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC {

  private int times;
  private int count = 0;

  private Lock lock = new ReentrantLock();
  private Condition c1 = lock.newCondition();
  private Condition c2 = lock.newCondition();
  private Condition c3 = lock.newCondition();

  public PrintABC(int times) {
    super();
    this.times = times;
  }

  public void print(String str, int target, Condition curr, Condition next) {
    for (int i = 0; i < times; ) {
      try {
        lock.lock();
        while (count % 3 != target) {
          curr.await();
        }
        System.out.println(str);
        //System.out.println(Thread.currentThread().getName());
        count++;
        i++;
        next.signalAll();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }
  }

  public void printA() {
    print("A", 0, c1, c2);
  }

  public void printB() {
    print("B", 1, c2, c3);
  }

  public void printC() {
    print("C", 2, c3, c1);
  }


  public static void main(String[] args) {
    PrintABC printABC = new PrintABC(3);
    Runnable r1 = printABC::printA;
    Runnable r2 = printABC::printB;
    Runnable r3 = printABC::printC;
    new Thread(r1).start();
    new Thread(r2).start();
    new Thread(r3).start();
  }

}

