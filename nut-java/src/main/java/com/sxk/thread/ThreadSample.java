package com.sxk.thread;

import com.sxk.entity.ConditionValidator;
import com.sxk.util.StringUtils;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadSample {

  public static void main(String[] args) throws Exception {
    threadMx();
    //testThreadLocal();
    testThreadStack(0);
    testThreadStack(1);
    testThreadStack(2);
    testThreadStack(3);
  }

  public static void threadMx() {
    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
    ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
    // 遍历线程信息，仅打印线程 ID 和线程名称信息
    for (ThreadInfo threadInfo : threadInfos) {
      System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
    }
  }

  private static void testThreadStack(int depth) {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

    if (stackTrace.length <= depth) {
      return;
    }
    StackTraceElement elem = stackTrace[depth];

    String str = String
        .format("%s(%s:%d)", elem.getMethodName(), elem.getFileName(), elem.getLineNumber());

    System.out.println(str);
  }

  private static void testThreadLocal() throws Exception {

    ExecutorService pool = Executors.newFixedThreadPool(8);
    for (int i = 0; i < 8; i++) {
      pool.submit(new RunTest());
    }
    pool.shutdown();
    pool.awaitTermination(1, TimeUnit.HOURS);

  }


  private static class RunTest implements Runnable {

    @Override
    public void run() {
      Random r = new Random();
      while (true) {
        ThreadSample.testCondition("a");
      }
    }

  }


  private static Map<String, ConditionValidator> conditionMap = new ConcurrentHashMap<>();

  public static void testCondition(String traitCode) {
    ConditionValidator conditionValidator = conditionMap
        .computeIfAbsent(traitCode, (k) -> new ConditionValidator(traitCode));

    String s = StringUtils.randomStr(new Random());
    conditionValidator.formatValidate(s);
    try {
      Thread.sleep(new Random().nextInt(100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //assert (conditionValidator.validate(traitCode) == true);
    System.out.println(traitCode + ":" + conditionValidator.validate(s));
    if (traitCode.equals("aa")) {
      System.out.println("size:" + conditionMap.size());
    }
  }

}
