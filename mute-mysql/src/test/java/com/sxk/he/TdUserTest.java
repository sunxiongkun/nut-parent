package com.sxk.he;

public class TdUserTest {

  static String url = "https://kms.talkingdata.com/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=searchPerson&rowsize=16&orderby=fdOrder&s_ajax=true&pageno=%d";


  //String cookiesStr = "xn_dvid_kf_9488=F31C15-985E2991-2ED0-341A-0180-A3D5DCE834D7; i18next=zh_cn; tdce=zhan.wang@tendcloud.com; comment_author=3b7259fc-8e42-4c4e-aafe-3e172e39c8ac; Hm_lvt_6b82c15e44f810b130c2eb92b0b36483=1581913391,1581920186,1581920540,1581920560; route=f3cdf988476e30eb1819b80c07c0e03a; JSESSIONID=0DFBC4C74A7A99D491742CF21DF615BA; LRToken=8447960fbce109a3869450fb8076d080052f2c8abf5605c7b961c3b2561b4524c2f81f87eab6c7fc2cd303fa51a4c740f499598baf10030fe924b93f5a7be4aab000130d91c53624348ac92e7d13231765ad73f80b1f2c15f11ed4dcdfd4d11b74dfe1c1f1bb49edd0a3afa2a5727622e3ccff3880c46e4bdce0b26e28223806";
  static String cookiesStr = "xn_dvid_kf_9488=F31C15-985E2991-2ED0-341A-0180-A3D5DCE834D7; i18next=zh_cn; route=f3cdf988476e30eb1819b80c07c0e03a; JSESSIONID=0DFBC4C74A7A99D491742CF21DF615BA; LRToken=8447960fbce109a3869450fb8076d080052f2c8abf5605c7b961c3b2561b4524c2f81f87eab6c7fc2cd303fa51a4c740f499598baf10030fe924b93f5a7be4aab000130d91c53624348ac92e7d13231765ad73f80b1f2c15f11ed4dcdfd4d11b74dfe1c1f1bb49edd0a3afa2a5727622e3ccff3880c46e4bdce0b26e28223806";

  public static void main(String[] args) {
    String[] array = cookiesStr.split(";");
    System.out.println(array.length);
    System.out.println(array[0]);
    System.out.println(array[1]);

    for (int i = 1; i < 30; i++) {

    }
  }


}
