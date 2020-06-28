package com.sxk.base;

public class ClassTest {

  /**
   * 向上委托 AppClassLoader->ExtClassLoader->BootClassLoader
   * 查找顺序 BootClassLoader->ExtClassLoader->AppClassLoader
   */
  public static void main(String[] args) {
    String bootClassPath = System.getProperty("sun.boot.class.path");
    System.out.println(bootClassPath);

    System.out.println(System.getProperty("java.class.path"));

    ClassLoader classLoader = ClassTest.class.getClassLoader();
    System.out.println("ClassTest ClassLoader is:" + classLoader.toString());
    System.out.println(
        "ClassTest ClassLoader Parent ClassLoader is:" + classLoader.getParent().toString());
    System.out.println(
        "ClassTest ClassLoader Parent ClassLoader Parent ClassLoader is:" + classLoader.getParent()
            .getParent().toString());

    ClassLoader cl = int.class.getClassLoader();
    System.out.println("int ClassLoader is:" + cl.toString());

    ClassLoader c2 = String.class.getClassLoader(); //BootStrapClassLoader
    System.out.println("String ClassLoader is:" + c2.toString());


  }
}
