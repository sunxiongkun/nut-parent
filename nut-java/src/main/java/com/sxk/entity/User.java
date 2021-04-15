package com.sxk.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private Integer id;
  private String name;
  private Integer age;
  private String mobile;
  private Date birthday;
  private String toUserName;
  private String fromUserName;
}
