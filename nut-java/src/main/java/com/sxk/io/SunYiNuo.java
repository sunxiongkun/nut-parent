package com.sxk.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SunYiNuo {

  static String SEPARATOR = ",";

  /*
    Map<Integer, String> map = IntStream.range(0, arr.length).mapToObj(x -> new Integer(x))
        .collect(Collectors.toMap(k -> k, k -> arr[k.intValue()]));
          System.out.println(map);
      */
  public static void main(String[] args) {
    String result = "E:\\usertag\\name\\names.txt";
//    extractName("E:\\usertag\\address\\saa\\saa", result);
//    extractName("E:\\usertag\\address\\saa\\sab", result);
//    extractName("E:\\usertag\\address\\saa\\sac", result);
//    extractName("E:\\usertag\\address\\saa\\sad", result);
//    extractName("E:\\usertag\\address\\saa\\sae", result);
//    extractName("E:\\usertag\\address\\saa\\saf", result);

    String line = null;
    File file = new File(result);
    List<String> names = new ArrayList<>();
    try (BufferedReader bs = new BufferedReader(new FileReader(file))) {
      while ((line = bs.readLine()) != null) {
        names.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    Random r = new Random();
    System.out.println(names.size());
    List<String> list = Arrays
        .asList("女士", "先生", "小姐", "强", "国", "学", "军", "均", "君", "爱", "中", "华", "福", "生", "玉", "波",
            "伟", "鑫", "鹏");
    int i = 0;
    while (i < 100) {
      String name = names.get(r.nextInt(names.size()));
      if (list.stream().mapToInt(x -> name.contains(x) ? 1 : 0).sum() == 0) {
        i++;
        System.out.println(name);
      }
    }
  }

  private static void extractName(String fromFile, String toFile) {
    File file = new File(fromFile);
    String[] arr = null;
    String line = null;
    String name = null;
    int i = 0;
    Set<String> names = new HashSet<>();
    try (BufferedReader bs = new BufferedReader(new FileReader(file))) {
      while ((line = bs.readLine()) != null) {
        i++;
        arr = line.split(SEPARATOR);
        name = arr[14];
        if (name.length() == 3) {
          names.add(name);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    File appendFile = new File(toFile);
    if (appendFile.exists()) {
      try (BufferedReader bs = new BufferedReader(new FileReader(appendFile))) {
        while ((line = bs.readLine()) != null) {
          names.add(line);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    System.out.println("nameSize:" + names.size());

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(appendFile))) {
      for (String result : names) {
        bw.write(result);
        bw.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
