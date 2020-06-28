package com.sxk.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import lombok.Data;
import org.junit.Test;

public class FourReference {

  /**
   * 强引用 对于强引用对象，即使堆空间不足的时候也不会被垃圾回收。
   */
  @Test
  public void test01() {
    RefObj obj = new RefObj();
    newBigByte();
  }

  /**
   * 将强引用置为null后，gc的时候才会被回收。
   */
  @Test
  public void test02() {
    RefObj obj = new RefObj();
    obj = null;
    newBigByte();
  }

  /**
   * 可以明显看到在堆空间充足的时候，jvm不会回收软引用对象，软引用对象只有在堆空间不足的时候才会被回收。
   */
  @Test
  public void test03() throws Exception {
    ReferenceQueue<RefObj> queue = new ReferenceQueue<>();
    SoftReference<RefObj> softReference = new SoftReference<RefObj>(new RefObj(), queue);
    System.out.println("堆空间充足的时候主动触发gc");
    System.gc();
    newBigByte();
    System.out.println(queue.poll());
  }

  /**
   * 对于弱引用对象，无论堆内存是否充足，只要发生gc，弱引用对象都会被回收。
   * 弱引用也可以结合ReferenceQueue来使用，在弱引用对象被回收的时候，
   * 其对应的reference对象会被放入queue中。
   */

  @Test
  public void test04() throws Exception {
    ReferenceQueue<RefObj> queue = new ReferenceQueue<>();
    WeakReference<RefObj> weakReference = new WeakReference<RefObj>(new RefObj(), queue);
    System.out.println("堆空间充足的时候主动触发gc");
    System.gc();
    //等待gc,回收的弱引用对象会在queue中
    Thread.sleep(1000);
    System.out.println(queue.poll());
  }

  /***
   * 虚引用必须和引用队列（ReferenceQueue）联合使用。
   * 当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，
   * 就会在回收对象的内存之前，把这个虚引用与之关联的引用队列中
   * @throws Exception
   */
  @Test
  public void test05() throws Exception {
    ReferenceQueue<RefObj> queue = new ReferenceQueue<>();
    PhantomReference<RefObj> weakReference = new PhantomReference<RefObj>(new RefObj(), queue);
    System.out.println("堆空间充足的时候主动触发gc");
    System.gc();
    //等待gc,回收的弱引用对象会在queue中
    Thread.sleep(1000);
    System.out.println(queue.poll());
  }

  public static void newBigByte() {
    System.out.println("new一个20M的byte数组");
    byte[] bytes = new byte[1024 * 1024 * 20];
  }

}

@Data
class RefObj {

  private Integer id;
  private String name;

  public RefObj() {
    System.out.println("对象创建...:" + this);
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    System.out.println("RefObj is gc...");
  }
}