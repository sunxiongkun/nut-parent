package com.sxk.collection.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Stack;

/**
 * @author sxk
 */
public class ArrayListAnalytics {

  public static void main(String[] args) {
    addAll();
    ensureCapacity(false);
    indexOutOfBoundsException();

    Stack<String> stack = new Stack<>();

  }

  public static void addAll() {
    List<String> list = new ArrayList<>(10);
    for (int i = 0; i < 5; i++) {
      list.add(i + "");
    }
    List<Object> allList = new ArrayList<>();
    allList.add("a");
    allList.add("b");
    allList.addAll(1, list);

    System.out.println(allList);

    final Spliterator<Object> spliterator = allList.spliterator();
    spliterator.forEachRemaining(System.out::println);

  }

  public static void indexOutOfBoundsException() {
    List<String> list = new ArrayList<>(10);
    list.add("b");
    list.set(0, "a");
    System.out.println(list);
    System.out.println(list.get(10));
  }

  public static void ensureCapacity(boolean ensure) {
    ArrayList<Object> list = new ArrayList<>();
    final int n = 10000000;
    long startTime1 = System.currentTimeMillis();
    if (ensure) {
      list.ensureCapacity(n);
    }
    for (int i = 0; i < n; i++) {
      list.add(i);
    }
    long endTime1 = System.currentTimeMillis();
    System.out.println("使用ensureCapacity " + ensure + " :用时" + (endTime1 - startTime1));
  }

}
