package com.sxk.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Shop {

  private Integer area;
  private Integer id;
  private String shopTitle;
  private String address;
  private String phone;
  private Integer yuYueLimit;
}
