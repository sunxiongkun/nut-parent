package com.sxk.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_he")
public class UserHe {

  @javax.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String srcId;
  private String idCard;
  private String mobile;
  private String name;
  private String address;
  private Date orderDate;
  private Date takeDate;
  private String takeTime;
}