package com.sxk.entity;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

  private Integer id;
  private String name;
  private Integer age;
  private String mobile;
  private Date birthday;
  private String toUserName;
  private String fromUserName;
}
