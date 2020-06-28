package com.sxk.util;

import com.alibaba.fastjson.JSONArray;
import com.sxk.entity.Shop;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ShopUtils {

  public static List<Shop> getAllShop() {
    List<Shop> result = null;
    InputStream is = ShopUtils.class.getClassLoader().getResourceAsStream("shop.data");
    StringBuilder sb;
    try (InputStreamReader isr = new InputStreamReader(is)) {
      sb = new StringBuilder();
      while (isr.ready()) {
        sb.append((char) isr.read());
      }
    } catch (Exception e) {
      e.printStackTrace();
      return result;
    }
    String shopStr = sb.toString();
    result = JSONArray.parseArray(shopStr, Shop.class);
    return result;
  }

}
