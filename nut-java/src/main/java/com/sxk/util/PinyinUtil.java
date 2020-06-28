package com.sxk.util;

import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Slf4j
public class PinyinUtil {

  private static Pattern CHINESE_CHAR_PATTERN = Pattern.compile("[\u4e00-\u9fa5]+");
  private static HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

  static {
    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 输出拼音全部小写
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 不带声调
    defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
  }

  /**
   * 将文字转为汉语拼音
   *
   * @param chineseLanguage 要转成拼音的中文
   */
  public static String toPinyin(String chineseLanguage) {
    char[] clChars = chineseLanguage.trim().toCharArray();
    StringBuilder sb = new StringBuilder();
    try {
      for (int i = 0; i < clChars.length; i++) {
        if (CHINESE_CHAR_PATTERN.matcher(String.valueOf(clChars[i])).find()) {// 如果字符是中文,则将中文转为汉语拼音
          sb.append(PinyinHelper.toHanyuPinyinStringArray(clChars[i], defaultFormat)[0]);
        } else {// 如果字符不是中文,则不转换
          sb.append(clChars[i]);
        }
      }
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      log.info("字符不能转成汉语拼音");
    }
    return sb.toString();
  }

  /**
   * 转换拼音字符串中第一个为大写
   */
  public static String getFirstLettersUp(String chineseLanguage) {
    return getFirstLetters(chineseLanguage, HanyuPinyinCaseType.UPPERCASE);
  }

  /**
   * 转换拼音字符串第一个为小写
   */
  public static String getFirstLettersLo(String chineseLanguage) {
    return getFirstLetters(chineseLanguage, HanyuPinyinCaseType.LOWERCASE);
  }

  /**
   * 获取第一个位置
   */
  public static String getFirstLetters(String chineseLanguage, HanyuPinyinCaseType caseType) {
    char[] clChars = chineseLanguage.trim().toCharArray();
    String hanyupinyin = "";
    HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    defaultFormat.setCaseType(caseType);// 输出拼音全部大写
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
    try {
      for (int i = 0; i < clChars.length; i++) {
        String str = String.valueOf(clChars[i]);
        if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
          hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(clChars[i], defaultFormat)[0]
              .substring(0, 1);
        } else if (str.matches("[0-9]+")) {// 如果字符是数字,取数字
          hanyupinyin += clChars[i];
        } else if (str.matches("[a-zA-Z]+")) {// 如果字符是字母,取字母
          hanyupinyin += clChars[i];
        } else {// 否则不转换
          hanyupinyin += clChars[i];//如果是标点符号的话，带着
        }
      }
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      log.error("字符不能转成汉语拼音");
    }
    return hanyupinyin;
  }

  /**
   * 获取拼音字符串
   */
  public static String getPinyinString(String chineseLanguage) {
    char[] clChars = chineseLanguage.trim().toCharArray();
    String hanyupinyin = "";
    HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部大写
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
    try {
      for (int i = 0; i < clChars.length; i++) {
        String str = String.valueOf(clChars[i]);
        if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
          hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(
              clChars[i], defaultFormat)[0];
        } else if (str.matches("[0-9]+")) {// 如果字符是数字,取数字
          hanyupinyin += clChars[i];
        } else if (str.matches("[a-zA-Z]+")) {// 如果字符是字母,取字母

          hanyupinyin += clChars[i];
        } else {// 否则不转换
        }
      }
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      log.error("字符不能转成汉语拼音");
    }
    return hanyupinyin;
  }

  /**
   * 取第一个汉字的第一个字符
   *
   * @return String
   * @Title: getFirstLetter
   * @Description: TODO
   */
  public static String getFirstLetter(String chineseLanguage) {
    char[] clChars = chineseLanguage.trim().toCharArray();
    String hanyupinyin = "";
    HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 输出拼音全部大写
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
    try {
      String str = String.valueOf(clChars[0]);
      if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
        hanyupinyin = PinyinHelper.toHanyuPinyinStringArray(
            clChars[0], defaultFormat)[0].substring(0, 1);
        ;
      } else if (str.matches("[0-9]+")) {// 如果字符是数字,取数字
        hanyupinyin += clChars[0];
      } else if (str.matches("[a-zA-Z]+")) {// 如果字符是字母,取字母

        hanyupinyin += clChars[0];
      } else {// 否则不转换

      }
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      log.error("字符不能转成汉语拼音");
    }
    return hanyupinyin;
  }

  /**
   * 测试程序入口
   */
  public static void main(String[] args) {
    System.out.println(PinyinUtil.toPinyin("测试汉语字符串"));
  }

}
