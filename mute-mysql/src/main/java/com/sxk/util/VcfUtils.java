package com.sxk.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class VcfUtils {

  static String newLine = System.getProperty("line.separator");
  static String NAME_PLACEHOLDER = "${NAME}";
  static String MOBILE_PLACEHOLDER = "${MOBILE}";

  static String template = "BEGIN:VCARD\n" + newLine
      + "VERSION:2.1\n" + newLine
      + "N;CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:${NAME};;;\n" + newLine
      + "FN;CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:${NAME}\n" + newLine
      + "TEL;CELL:${MOBILE}\n" + newLine
      + "END:VCARD" + newLine;

  /**
   * =E5=8E=BB=E5=93=AA=E4=BA=8C=E9=9D=A2
   * 去哪二面
   */

  public static String genVcardStr(String name, String mobile) {
    String hexName = toChineseHex(name);
    return template.replace(NAME_PLACEHOLDER, hexName).replace(MOBILE_PLACEHOLDER, mobile);
  }


  public static String toChineseHex(String s) {
    String ss = s;
    byte[] bt = ss.getBytes(StandardCharsets.UTF_8);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bt.length; i++) {
      String tempStr = Integer.toHexString(bt[i]);
      if (tempStr.length() > 2) {
        tempStr = tempStr.substring(tempStr.length() - 2);
      }
      sb.append("=");
      sb.append(tempStr);
    }
    return sb.toString().toUpperCase();
  }


  public static void main(String[] args) {

    try (FileOutputStream fos = new FileOutputStream(
        "/data/00002.vcf"); BufferedWriter bw = new BufferedWriter(
        new OutputStreamWriter(fos, StandardCharsets.UTF_8))) {
      bw.write(genVcardStr("石家庄孙", "18710265851"));
      bw.write(genVcardStr("北京孙", "18743923895"));
      bw.write(genVcardStr("北京刘", "15210765773"));
      bw.write(genVcardStr("北京郑", "13224394303"));
      bw.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
