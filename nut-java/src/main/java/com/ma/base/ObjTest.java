package com.ma.base;

import java.util.concurrent.atomic.AtomicInteger;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class ObjTest {

  private static class T {

    //private int age;
    //private Map<String, Integer> map;

  }

  public static void main(String[] args) throws Exception{
    //objPrint();
    objLock();
  }


  private static void objPrint() throws Exception{
    T o = new T();
    String objPrintStr = ClassLayout.parseInstance(o).toPrintable();
    System.out.println(objPrintStr);

    System.out.println("==============");

    String objGraphPrintStr = GraphLayout.parseInstance(o).toPrintable();
    System.out.println(objGraphPrintStr);
  }

  private static void objLock() throws Exception{
    Thread.sleep(5000);

    T o = new T();

    String objPrintStr = ClassLayout.parseInstance(o).toPrintable();
    System.out.println(objPrintStr);
    synchronized (o) {
      objPrintStr = ClassLayout.parseInstance(o).toPrintable();
      System.out.println(objPrintStr);
    }
    objPrintStr = ClassLayout.parseInstance(o).toPrintable();
    System.out.println(objPrintStr);
  }

  private static void atomicLock() {
    AtomicInteger count = new AtomicInteger();
    count.getAndIncrement();
  }

}
