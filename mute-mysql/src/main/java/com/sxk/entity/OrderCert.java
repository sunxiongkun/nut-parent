package com.sxk.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_cert_image")
public class OrderCert {

  @javax.persistence.Id
  private String id;
  private String orderId;
  private String certNo;
  private String certName;
  private String headImageurl;
  private String certImageurl;
  private Long createTime;
  private Long createFinishTime;
  private Integer isChecked;
  private String showId;
}