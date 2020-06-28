package com.sxk.xml;

import com.sxk.entity.User;
import com.sxk.entity.WxMsg;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.util.Date;

public class XmlTest {


  /**
   * <xml>
   * <ToUserName><![CDATA[toUser]]></ToUserName>
   * <FromUserName><![CDATA[FromUser]]></FromUserName>
   * <CreateTime>123456789</CreateTime>
   * <MsgType><![CDATA[event]]></MsgType>
   * <Event><![CDATA[WifiConnected]]></Event>
   * <ConnectTime>0</ConnectTime>
   * <ExpireTime>0</ExpireTime>
   * <VendorId>![CDATA[3001224419]]</VendorId>
   * <ShopId>![CDATA[PlaceId]]</ShopId>
   * <DeviceNo>![CDATA[DeviceNo]]</DeviceNo>
   * </xml>
   */
  public static void main(String[] args) {

    String xml0 = "<xml> \n"
        + "<ToUserName><![CDATA[toUser]]></ToUserName> \n"
        + "<FromUserName><![CDATA[FromUser]]></FromUserName> \n"
        + "<CreateTime>123456789</CreateTime> \n"
        + "<MsgType><![CDATA[event]]></MsgType> \n"
        + "<Event><![CDATA[WifiConnected]]></Event> \n"
        + "<ConnectTime>0</ConnectTime>\n"
        + "<ExpireTime>0</ExpireTime>\n"
        + "<VendorId>![CDATA[3001224419]]</VendorId>\n"
        + "<ShopId>![CDATA[PlaceId]]</ShopId>\n"
        + "<DeviceNo>![CDATA[DeviceNo]]</DeviceNo>\n"
        + "</xml>";

    User u = User.builder().id(1).name("name").age(10).mobile("139").birthday(new Date()).build();

    // XStream xstream = new XStream();
    XStream xstream = new XStream(new StaxDriver());
    String xml = xstream.toXML(u);
    WxMsg wxMsg = new WxMsg();
    xstream.setClassLoader(wxMsg.getClass().getClassLoader());

    System.out.println(wxMsg.getClass().getClassLoader());
    System.out.println(xstream.getClass().getClassLoader());
    xstream.alias("xml", WxMsg.class);
    System.out.println(xml0);
    WxMsg u2 = (WxMsg) xstream.fromXML(xml0, wxMsg);
    System.out.println(u2);
  }

}
