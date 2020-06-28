package com.sxk.he;

import com.sxk.dao.OrderCertRepository;
import com.sxk.entity.OrderCert;
import com.sxk.util.HttpUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class OrderCertTest {

  @Autowired
  private OrderCertRepository dao;

  /**
   * http://lerentang.yihecm.com/ 等于 http://39.100.143.168/
   */

  static String ID_CARD_PATH = "E:/wp/idcard/40/%s_%s.jpg";

  @Test
  public void testSelect() throws Exception {
    Iterable<OrderCert> all = dao.findAll(PageRequest.of(3, 20));
    all.forEach(System.out::println);

    ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(100);
    ThreadPoolExecutor pool = new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS,
        blockingQueue);

    List<Future<String>> allResult = new ArrayList<>();

    String str = null;

    Iterator<OrderCert> it = all.iterator();
    int i = 0;
    while (it.hasNext()) {
      i++;
      OrderCert next = it.next();
      str = next.getCertName() + ":" + next.getCertNo();

      PostLerentang.submitAndPut(str, pool, allResult);
    }

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


  @Test
  public void testDownImage() throws Exception {
    Iterable<OrderCert> all = dao.findAll(PageRequest.of(2, 20));
    Iterator<OrderCert> it = all.iterator();
    int i = 0;
    while (it.hasNext()) {
      OrderCert next = it.next();
      File tmpFile = HttpUtils.download(next.getCertImageurl());
      File imageFile = new File(String.format(ID_CARD_PATH, next.getCertName(), next.getCertNo()));
      if (imageFile.exists()) {
        continue;
      }
      FileCopyUtils.copy(tmpFile, imageFile);
      tmpFile.delete();
    }
  }


}
