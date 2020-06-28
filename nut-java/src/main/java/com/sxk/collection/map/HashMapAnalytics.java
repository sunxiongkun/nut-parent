package com.sxk.collection.map;

import com.sxk.util.StringUtils;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HashMapAnalytics {

  private static HashMap<String, String> map = new HashMap<>();

  public static void main(String[] args) throws Exception {
    int hashCode = "A".hashCode();
    System.out.println(hashCode >>> 16);
    System.out.println(hashCode ^ (hashCode >>> 16));
    //testHighCpu();
    putElement(100);
  }

  static void testHighCpu() throws Exception {
    ExecutorService pool = Executors.newFixedThreadPool(8);

    System.out.println("start...");
    Runnable task1 = () -> putElement(10000000);
    Runnable task2 = () -> putElement(10000000);
    Runnable task3 = () -> putElement(10000000);
    Runnable task4 = () -> putElement(10000000);
    Runnable task5 = () -> putElement(10000000);
    pool.submit(task1);
    pool.submit(task2);
    pool.submit(task3);
    pool.submit(task4);
    pool.submit(task5);
    pool.shutdown();
    pool.awaitTermination(1, TimeUnit.HOURS);
    System.out.println("end...");

  }

  static void putElement(int num) {
    Random r = new Random();
    for (int i = 0; i < num; i++) {
      String key = StringUtils.randomStr(r) + i;
      map.put(key, key);
    }
  }


}
