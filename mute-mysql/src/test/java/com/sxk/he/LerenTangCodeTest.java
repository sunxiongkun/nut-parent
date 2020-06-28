package com.sxk.he;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.springframework.util.FileCopyUtils;

public class LerenTangCodeTest {

  static String path = "/data/lerentang/erweima.html";
  static String ROOT_PATH = "/data/lerentang/erweima/%s_%s.html";

  static String ID_PATTERN = "10786";


  public static void main(String[] args) {
    String[] array = {"10786", "10809", "12434", "12437", "12547"};
    for (int i = 0; i < array.length; i++) {
      generateCode("机场路", array[i]);
    }

  }

  public static void generateCode(String address, String id) {
    try {
      FileReader fileReader = new FileReader(new File(path));
      String result = FileCopyUtils.copyToString(fileReader);
      String res = result.replaceAll(ID_PATTERN, id);
      File file = new File(String.format(ROOT_PATH, address, id));
      OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
      writer.write(res);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
