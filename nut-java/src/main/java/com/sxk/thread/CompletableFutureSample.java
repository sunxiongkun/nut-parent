package com.sxk.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * java8 CompletableFuture
 */
public class CompletableFutureSample {

  public static void main(String[] args) throws Exception {
    //featureGet();
    //runAsync();
    //supplyAsync();
    testThenCombine();
  }


  /**
   * 默认会从ForkJoinPool.commonPool()中获取线程执行
   * ,但是你也可以创建一个线程池并传给runAsync() 和supplyAsync()方法来让他们从线程池中获取一个线程执行它们的任务。
   * static CompletableFuture<Void>  runAsync(Runnable runnable)
   * static CompletableFuture<Void>  runAsync(Runnable runnable, Executor executor)
   * static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
   * static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
   */

  public static void featureGet() throws Exception {
    CompletableFuture<String> completableFuture = new CompletableFuture<String>();
    completableFuture.complete("completable future finish");
    String str = completableFuture.get();
    System.out.println(str);
  }

  public static void runAsync() throws Exception {
    // Using Lambda Expression
    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
      // Simulate a long-running Job
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      System.out.println("I'll run in a separate thread than the main thread.");
    }).thenRun(() -> System.out.println("finish"));

    TimeUnit.SECONDS.sleep(2);

  }

  /**
   * thenApply(), thenAccept() 和thenRun()方法附上一个回调给CompletableFuture
   * 使用 thenCompose() 组合两个独立的future
   */

  public static void supplyAsync() throws Exception {
    // Create a CompletableFuture
    CompletableFuture<String> whatsYourNameFuture = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      return "Rajeev";
    });

    // Attach a callback to the Future using thenApply()
    CompletableFuture<String> greetingFuture = whatsYourNameFuture.thenApply(name -> {
      return "Hello " + name;
    });

    CompletableFuture<String> finalFuture = greetingFuture.thenApply(greeting -> {
      return greeting + ", Welcome to the CalliCoder Blog";
    });

    System.out.println(finalFuture.get());
  }

  private static void testThenCombine() throws Exception {
    System.out.println("Retrieving weight.");
    CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      return 65.0;
    });

    System.out.println("Retrieving height.");
    CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      return 177.8;
    });

    System.out.println("Calculating BMI.");
    CompletableFuture<Double> combinedFuture = weightInKgFuture
        .thenCombine(heightInCmFuture, (weightInKg, heightInCm) -> {
          Double heightInMeter = heightInCm / 100;
          return weightInKg / (heightInMeter * heightInMeter);
        });

    System.out.println("Your BMI is - " + combinedFuture.get());

  }

  static CompletableFuture<String> downloadWebPage(String pageLink) {
    return CompletableFuture.supplyAsync(() -> {
      // Code to download and return the web page's content
      return pageLink + " is finish";
    });
  }

  private static void testAllOf() {

    List<String> webPageLinks = Arrays
        .asList("1.html", "2.html");    // A list of 100 web page links

    // Download contents of all the web pages asynchronously
    List<CompletableFuture<String>> pageContentFutures = webPageLinks.stream()
        .map(webPageLink -> downloadWebPage(webPageLink))
        .collect(Collectors.toList());

    // Create a combined Future using allOf()
    CompletableFuture<Void> allFutures = CompletableFuture.allOf(
        pageContentFutures.toArray(new CompletableFuture[pageContentFutures.size()])
    );

    // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -
    CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v -> {
      return pageContentFutures.stream()
          .map(pageContentFuture -> pageContentFuture.join())
          .collect(Collectors.toList());
    });
  }

  private static void testAnyOf() throws Exception {
    CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      return "Result of Future 1";
    });

    CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      return "Result of Future 2";
    });

    CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      return "Result of Future 3";
    });

    CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);

    System.out.println(anyOfFuture.get()); // Result of Future 2
  }

  public static void testFutureException() {
    int age = 20;
    CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
      if (age < 0) {
        throw new IllegalArgumentException("Age can not be negative");
      }
      if (age > 18) {
        return "Adult";
      } else {
        return "Child";
      }
    });

    maturityFuture.exceptionally(ex -> {
      System.out.println("Oops! We have an exception - " + ex.getMessage());
      return "Unknown!";
    });

    maturityFuture.handle((res, ex) -> {
      if (ex != null) {
        System.out.println("Oops! We have an exception - " + ex.getMessage());
        return "Unknown!";
      }
      return res;
    });

    maturityFuture.thenAccept(result -> System.out.println(result));
  }

}
