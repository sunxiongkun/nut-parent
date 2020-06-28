package com.sxk.base;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JavaEnvPrint {

  public static void main(String[] args) throws Exception {
    Map<String, String> env = System.getenv();

    System.out.println("USER:" + env.get("USER"));
    System.out.println("LOGNAME:" + env.get("LOGNAME"));
    System.out.println("HOME:" + env.get("HOME"));
    System.out.println("PWD:" + env.get("PWD"));
    System.out.println("PATH:" + env.get("PATH"));
    System.out.println("SHELL:" + env.get("SHELL"));
    System.out.println("JAVA_HOME:" + env.get("JAVA_HOME"));
    System.out.println("CLASSPATH:" + env.get("CLASSPATH"));
    System.out.println("APP_CLASSPATH:" + env.get("APP_CLASSPATH"));
    System.out.println("LD_LIBRARY_PATH:" + env.get("LD_LIBRARY_PATH"));

    File fp = File.createTempFile("abc", null);
    System.out.println(fp.getPath());
    System.out.println(fp.getAbsolutePath());
    System.out.println(fp.toURI());
    fp.deleteOnExit();

    Map<String,String> m1=new HashMap<>();
    Map<String,String> m2=new HashMap<>();
    m1.put("a","1");
    m1.put("b","2");
    m2.put("b",null);
    m2.put("c","3");

    System.out.println(m1);
    System.out.println(m2);
    m1.putAll(m2);
    System.out.println(m1);

    Thread.sleep(60000);
  }

}
