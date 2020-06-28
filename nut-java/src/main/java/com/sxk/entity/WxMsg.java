package com.sxk.entity;

import lombok.Data;

@Data
public class WxMsg {

  private String toUserName;     // 文本 图片 语音 视频 小视频 地理位置 链接 事件
  private String fromUserName;   // 文本 图片 语音 视频 小视频 地理位置 链接 事件
  private String createTime;     // 文本 图片 语音 视频 小视频 地理位置 链接 事件
  private String msgType;        // 文本 图片 语音 视频 小视频 地理位置 链接 事件
  private String msgId;          // 文本 图片 语音 视频 小视频 地理位置 链接
  private String event;          // 　　 　　 　　 　　 　　　 　　　　 　　 事件
  private String eventKey;       // 　　 　　 　　 　　 　　　 　　　　 　　 事件：扫描参数二维码、关注公众号、按钮点击
  private String menuId;         // 　　 　　 　　 　　 　　　 　　　　 　　 事件：按钮点击
  private String content;        // 文本
  private String picUrl;         // 　　 图片
  private String mediaId;        // 　　 图片 语音 视频 小视频
  private String format;         // 　　 　　 语音
  private String recognition;    // 　　 　　 语音
  private String thumbMediaId;   // 　　 　　 　　 视频 小视频
  private String location_X;     // 　　 　　 　　 　　 　　　 地理位置
  private String location_Y;     // 　　 　　 　　 　　 　　　 地理位置
  private String scale;          // 　　 　　 　　 　　 　　　 地理位置
  private String label;          // 　　 　　 　　 　　 　　　 地理位置
  private String title;          // 　　 　　 　　 　　 　　　 　　　　 链接
  private String description;    // 　　 　　 　　 　　 　　　 　　　　 链接
  private String url;            // 　　 　　 　　 　　 　　　 　　　　 链接
  private String ticket;         // 　　 　　 　　 　　 　　　 　　　　 　　 事件：扫描参数二维码、关注公众号
  private String latitude;       // 　　 　　 　　 　　 　　　 　　　　 　　 事件：上报地理位置
  private String longitude;      // 　　 　　 　　 　　 　　　 　　　　 　　 事件：上报地理位置
  private String precision;      // 　　 　　 　　 　　 　　　 　　　　 　　 事件：上报地理位置

}
