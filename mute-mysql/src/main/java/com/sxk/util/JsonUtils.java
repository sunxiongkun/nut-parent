package com.sxk.util;

import com.alibaba.fastjson.JSONArray;
import com.sxk.entity.Shop;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonUtils {

  public static void main(String[] args) throws Exception {
    InputStream is = JsonUtils.class.getClassLoader().getResourceAsStream("shop.data");
    InputStreamReader isr = new InputStreamReader(is);
    StringBuilder sb = new StringBuilder();
    while (isr.ready()) {
      sb.append((char) isr.read());
    }
    String shopStr = sb.toString();

    JSONArray jsonArray = JSONArray.parseArray(shopStr);
    System.out.println("shopSize:" + jsonArray.size());
    jsonArray.forEach(System.out::println);

    List<Shop> shopList = JSONArray.parseArray(shopStr, Shop.class);
    System.out.println("========================");
    for (int i = 0; i < shopList.size(); i++) {
      Shop obj = shopList.get(i);
      if (obj.getYuYueLimit() > 150 && obj.getArea() == 15) {
        System.out.println(obj);
      }
    }

  }

}
