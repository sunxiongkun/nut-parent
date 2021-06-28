package com.sxk.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sxk
 * @date 2021/5/3 5:27 下午
 */
public class XiyuanGuahao {

  public static void main(String[] args) {
  }

  public static void getSession() {
    String url = "https://wxmini.xyhospital.com/api/patient/card/getDefaultCard";

    Map<String, String> params = new HashMap<>();
    final String res = HttpUtils.post(url, params);

  }

}
