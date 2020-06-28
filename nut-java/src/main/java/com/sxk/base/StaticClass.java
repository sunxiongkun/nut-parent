package com.sxk.base;

public class StaticClass {

  public static long OUTER_DATE = System.nanoTime();

  static {
    System.out.println("外部类-静态块加载时间：" + System.nanoTime());
  }

  public StaticClass() {
    System.out.println("外部类-构造函数时间：" + System.nanoTime());
  }

  static class InnerStaticClass {

    public static long INNER_STATIC_DATE = System.nanoTime();

    static {
      System.out.println("静态内部类-静态块加载时间：" + System.nanoTime());
    }
  }

  class InnerClass {

    public long INNER_DATE = 0;

    public InnerClass() {
      INNER_DATE = System.nanoTime();
    }
  }

  /**
   * 一个类被加载，当且仅当其某个静态成员（静态域、构造器、静态方法等）被调用时发生
   */
  public static void main(String[] args) {
    System.out.println(StaticClass.OUTER_DATE);
    //StaticClass outer = new StaticClass();
    //System.out.println("外部类-静态变量加载时间：" + outer.OUTER_DATE);
    //System.out.println("非静态-内部类加载时间" + outer.new InnerClass().INNER_DATE);
    //System.out.println("静态内部类-加载时间：" + InnerStaticClass.INNER_STATIC_DATE);
  }
}
