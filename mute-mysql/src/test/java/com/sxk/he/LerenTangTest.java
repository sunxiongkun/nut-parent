package com.sxk.he;

import com.sxk.entity.UserHe;
import com.sxk.util.HttpUtils;
import com.sxk.util.LocalDateUtils;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

public class LerenTangTest {

  //static String rootUrl = "http://lerentang.yihecm.com/?m=ewm&id=1927";
  static String rootPath = "/data/sxk/%d.html";
  static String rootUrl = "http://lerentang.yihecm.com/?m=check&id=%d";
  static String selectUrl = "http://lerentang.yihecm.com/?m=resultchaxun";

  public static void main(String[] args) {

    for (int k = 59300; k < 66000; k += 100) {
      final int start = k;
      final int end = k + 100;
      Runnable t = () -> {
        int id = 0;
        for (int i = start; i < end; i++) {
          try {
            id = i;
            File htmlFile = new File(String.format(rootPath, id));
            if (htmlFile.exists()) {
              System.out.println("file exists:" + htmlFile.getPath());
              continue;
            }

            File tmpFile = HttpUtils.download(String.format(rootUrl, id), 20000);
            int j = 0;
            while (tmpFile == null) {
              tmpFile = HttpUtils.download(String.format(rootUrl, id), 20000);
              //Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
              if (j++ > 20) {
                System.out.println("id:" + id + ":loop:" + j);
                break;
              }
            }
            if (tmpFile == null) {
              continue;
            }
            FileCopyUtils.copy(tmpFile, htmlFile);
            tmpFile.delete();
            if (id % 100 == 0) {
              System.out.println("id:" + id);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };
      new Thread(t).start();
    }

  }

  @Test
  public void testAnalyticsFile() throws SQLException {

    Set<String> idCardSet = new HashSet<>();

    Map<String, List<String>> map = new HashMap<>();
    for (int i = 59300; i < 66000; i++) {
      File file = new File(String.format(rootPath, i));
      if (!file.exists()) {
        continue;
      }
      try {
        FileReader fileReader = new FileReader(file);
        String result = FileCopyUtils.copyToString(fileReader);
        Document doc = Jsoup.parse(result);
        Elements inputList = doc.select(".masseget");

        if (inputList == null || inputList.size() == 0) {
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

        if (PostLerentang.PHONE_LIST.contains(userHe.getMobile())) {
          System.out.println(userHe);

          if (map.containsKey(userHe.getAddress())) {
            map.get(userHe.getAddress())
                .add(String.format("%s:%d  %s", userHe.getName(), i, userHe.getIdCard()));
          } else {
            ArrayList<String> list = new ArrayList<>();
            list.add(String.format("%s:%d  %s", userHe.getName(), i, userHe.getIdCard()));
            map.put(userHe.getAddress(), list);
          }
        }

      } catch (Exception e) {
        System.out.println("error:" + i);
        //System.out.println("损坏的文件:" + file.getPath());
        //file.delete();
        e.printStackTrace();
      }
    }

    for (Map.Entry en : map.entrySet()) {
      System.out.println(en.getKey());
      System.out.println(en.getValue());
    }

    System.out.println("idCardSet:" + idCardSet.size());
  }


  @Test
  public void testDownload() throws Exception {
    String url = "http://lerentang.yihecm.com/?m=yuyuelist&id=1";
    File tmpFile = HttpUtils.download(url, 20000);
    int i = 0;
    while (tmpFile == null) {
      tmpFile = HttpUtils.download(url, 20000);
      System.out.println("loop:" + i++);
    }
    FileCopyUtils.copy(tmpFile, new File("/data/yuyuelist.html"));
  }

}
