package com.sxk.base.parent;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SonUser extends ParentUser {

  private Integer age;
  private Date birthday;


//  public SonUser() {
//    //super(); //因为父类没有实现无参构造函数，所以会报错
//    this.age = 1;
//  }

  public SonUser(Integer id, String name) {
    super(id, name);
    this.age = 1;
  }


  public static void main(String[] args) {
    SonUser sonUser = new SonUser(1,"a");
    System.out.println(sonUser);

  }

}
