package com.sxk.he;

import com.sxk.dao.UserHeRepository;
import com.sxk.entity.Shop;
import com.sxk.entity.UserHe;
import com.sxk.util.LocalDateUtils;
import com.sxk.util.ShopUtils;
import com.sxk.util.VcfUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class UserHeTest {

  @Autowired
  private UserHeRepository dao;


  @Test
  public void testSelect() {
    Iterable<UserHe> all = dao.findAll();
    all.forEach(System.out::println);
  }

  static String rootPath = "/data/sxk/%d.html";


  @Test
  public void testInsert() {
    Set<String> idCardSet = new HashSet<>();

    for (int i = 59421; i < 70000; i++) {
      try {
        File file = new File(String.format(rootPath, i));
        if (!file.exists()) {
          continue;
        }
        FileReader fileReader = new FileReader(file);
        String result = FileCopyUtils.copyToString(fileReader);
        Document doc = Jsoup.parse(result);
        Elements inputList = doc.select(".masseget");

        if (null == inputList || inputList.size() == 0) {
          System.out.println("损坏的文件:" + file.getPath());
          file.delete();
          continue;
        }
        UserHe userHe = new UserHe();
        for (int j = 0; j < inputList.size(); j++) {
          Element input = inputList.get(j);
          String value = input.attr("value");
          int index = value.indexOf(":");
          if (index < 0) {
            continue;
          }
          String key = value.substring(0, index);
          String res = value.substring(index + 1);
          switch (key) {
            case "姓名":
              userHe.setName(res);
              break;
            case "身份证":
              userHe.setIdCard(res);
              idCardSet.add(res);
              break;
            case "电话":
              userHe.setMobile(res);
              break;
            case "预约时间":
              userHe.setOrderDate(new Date(LocalDateUtils.convertTimeToLong(res)));
              break;
            case "取货时间":
              String takeDate = res;
              String[] takeDateArr = takeDate.split("  ");
              if (takeDateArr.length == 2) {
                userHe.setTakeDate(new Date(LocalDateUtils.convertDateToLong(takeDateArr[0])));
                userHe.setTakeTime(takeDateArr[1]);
              }
              break;
            case "预约门店":
              userHe.setAddress(res);
              break;
            default:
              break;
          }
        }

        userHe.setSrcId(i + "");
        dao.save(userHe);

        if (userHe.getName().startsWith("孙") && userHe.getAddress().equals("阳光店")) {
          System.out.println(userHe);
        }
      } catch (Exception e) {
        System.out.println("error:" + i);
        e.printStackTrace();
      }
    }

    System.out.println("idCardSet:" + idCardSet.size());
  }


  static String vcfRootPath = "/data/vcf/00%s.vcf";

  @Test
  public void genVcardFile() {

    List<Shop> allShop = ShopUtils.getAllShop();

    Map<String, Shop> shopNameMap = allShop.stream()
        .collect(Collectors.toMap(Shop::getShopTitle, Function.identity()));

    System.out.println(shopNameMap);

    for (int i = 0; i < 100; i++) {
      PageRequest pageRequest = PageRequest.of(0, 100);
      Page<UserHe> all = dao.findAll(pageRequest);
      if (all.isEmpty()) {
        continue;
      }

      String fileName =
          i < 10 ? String.format(vcfRootPath, "0" + (i + 1))
              : String.format(vcfRootPath, (i + 1) + "");
      try (FileOutputStream fos = new FileOutputStream(
          fileName); BufferedWriter bw = new BufferedWriter(
          new OutputStreamWriter(fos, StandardCharsets.UTF_8))) {

        Iterator<UserHe> iterator = all.iterator();
        while (iterator.hasNext()) {
          UserHe next = iterator.next();
          String address = next.getAddress();
          if (!shopNameMap.containsKey(address) || shopNameMap.get(address).getArea() != 3) {
            System.out.println(next);
            continue;
          }
          bw.write(VcfUtils.genVcardStr(next.getName(), next.getMobile()));
        }
        bw.flush();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }


  }
}
