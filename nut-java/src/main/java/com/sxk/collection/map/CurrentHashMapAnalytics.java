package com.sxk.collection.map;

import java.util.concurrent.ConcurrentHashMap;

public class CurrentHashMapAnalytics {

  public static void main(String[] args) {
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    map.put("", "");
    map.get("");
  }

}
