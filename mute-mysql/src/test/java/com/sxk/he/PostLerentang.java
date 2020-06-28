package com.sxk.he;

import com.sxk.entity.Shop;
import com.sxk.util.HttpUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PostLerentang {

  static String url = "http://lerentang.yihecm.com/?m=save";

  static List<Shop> SHOP_LIST = new ArrayList<>();

  static Map<String, String> INIT_PARAMS = new HashMap<>();

  static List<String> PHONE_LIST = Arrays
      .asList("15830131311", "13833001011", "18210532005", "15830131312");

  static {
    SHOP_LIST.add(Shop.builder().area(3).id(22).shopTitle("乐仁堂金马店").build());
    SHOP_LIST.add(Shop.builder().area(3).id(52).shopTitle("机场路店").build());
    SHOP_LIST.add(Shop.builder().area(3).id(53).shopTitle("新华店").build());
    SHOP_LIST.add(Shop.builder().area(3).id(34).shopTitle("益福店").build());
    SHOP_LIST.add(Shop.builder().area(3).id(47).shopTitle("永兴店").build());
    SHOP_LIST.add(Shop.builder().area(3).id(54).shopTitle("人民店").build());

//    SHOP_LIST.add(Shop.builder().area(3).id(25).shopTitle("乐仁堂合作路店").build());
//    SHOP_LIST.add(Shop.builder().area(15).id(35).shopTitle("阳光店").build());
//    SHOP_LIST.add(Shop.builder().area(15).id(50).shopTitle("新光店").build());

    INIT_PARAMS.put("pickdate", "2020-02-17");
    INIT_PARAMS.put("picktime", "11:00-13:00");
    INIT_PARAMS.put("shuliang", "5");
    INIT_PARAMS.put("pid", "1");
  }


  public static void main(String[] args) throws Exception {

    ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(100);
    ThreadPoolExecutor pool = new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS,
        blockingQueue);

    List<Future<String>> allResult = new ArrayList<>();

    String str = "孙彦照:130131198808011518";
    submitAndPut(str, pool, allResult);

//    str = "孙爱军:61011319710908212x";
//    submitAndPut(str, pool, allResult);
//    str = "孙天问:130104199706232112";
//    submitAndPut(str, pool, allResult);
//    str = "孙庆:130102196904051252";
//    submitAndPut(str, pool, allResult);

//    str = "孙国强:132325196506074210";
//    submitAndPut(str,  pool, allResult);
//    str = "孙昆鹏:130182199209235311";
//    submitAndPut(str,  pool, allResult);
//    str = "孙中华:132325196501254212";
//    submitAndPut(str,  pool, allResult);
//    str = "齐雪敏:132325196601314227";
//    submitAndPut(str,  pool, allResult);

    //========================================
    str = "郝伟鑫:130182199001156323";
    submitAndPut(str, pool, allResult);
    str = "郝伟霞:130182198612206241";
    submitAndPut(str, pool, allResult);
    str = "郝福进:132325195606095436";
    submitAndPut(str, pool, allResult);
    str = "郝伟玉:130182198207126221";
    submitAndPut(str, pool, allResult);
//    //=================================

//    str = "孙学均:132325196707124245";
//    submitAndPut(str, pool, allResult);
//    str = "孙振:360102198504083812";
//    submitAndPut(str, pool, allResult);
//    str = "孙超逸:110108198704053716";
//    submitAndPut(str, pool, allResult);
//
//    str = "刘芳池:220621199012010529";
//    submitAndPut(str,  pool, allResult);
//    str = "刘福生:220621196510150539";
//    submitAndPut(str,  pool, allResult);
//    str = "郑玉波:22062119670502054X";
//    submitAndPut(str,  pool, allResult);

    pool.shutdown();

    while (!pool.awaitTermination(20, TimeUnit.MINUTES)) {
      System.out.println("wait..." + pool);
    }

    allResult.forEach(future -> {
      try {
        System.out.println(future.get());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

  }


  public static void submitAndPut(String str, ThreadPoolExecutor pool,
      List<Future<String>> allResult) {

    CountDownLatch countDownLatch = new CountDownLatch(1);

    for (int i = 0; i < SHOP_LIST.size(); i++) {
      allResult.add(pool.submit(new PostOrder(str, countDownLatch, SHOP_LIST.get(i))));
    }

    //allResult.add(pool.submit(new PostOrder(str, countDownLatch)));
  }


  static class PostOrder implements Callable<String> {

    private Map<String, String> params = new HashMap<>();
    private CountDownLatch countDownLatch;

    PostOrder(String str, CountDownLatch countDownLatch) {
      this(str, countDownLatch,
          SHOP_LIST.get(ThreadLocalRandom.current().nextInt(SHOP_LIST.size())));
    }

    PostOrder(String str, CountDownLatch countDownLatch, Shop shop) {
      this.params.putAll(INIT_PARAMS);
      String[] array = str.split(":");
      this.params.put("realname", array[0]);
      this.params.put("shenfenzheng", array[1]);

      this.params
          .put("phone", PHONE_LIST.get(ThreadLocalRandom.current().nextInt(PHONE_LIST.size())));

      this.params.put("area", shop.getArea() + "");
      this.params.put("shop", shop.getId() + "");

      System.out.println("PostOrder:" + this.params);
      this.countDownLatch = countDownLatch;
    }


    @Override
    public String call() throws Exception {
      Thread.currentThread().setName("thread-" + params.get("realname") + "-" + params.get("shop"));
      String result = HttpUtils.post(url, params, null, null, 10000);
      int i = 0;
      // success : [1,"23396","2020-02-11 19:32:12"]
      while (result == null || !result.startsWith("[1")) {
        result = HttpUtils.post(url, params, null, null, 30000);
        i++;

        if (result != null && result.length() < 1000) {
          System.out.println(params.get("realname") + ":loop:" + i + ":" + result);
        } else {
          System.out
              .println(params.get("realname") + ":loop:" + i);
        }

        if (countDownLatch.getCount() == 0) {
          System.out.println("threadName break:" + Thread.currentThread().getName());
          break;
        }
      }

      System.out.println(params.get("realname") + "=====" + result);

      //如果有一个线程成功 countDown
      countDownLatch.countDown();

      return params.get("realname") + " = " + result;
    }
  }


}


