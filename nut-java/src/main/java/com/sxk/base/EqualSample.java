package com.sxk.base;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EqualSample {

  private Integer id;
  private String name;

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof EqualSample) {
      EqualSample other = (EqualSample) obj;
      return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, name);
  }

  public static void main(String[] args) {
    EqualSample eq1 = new EqualSample();
    eq1.setId(120);
    EqualSample eq2 = new EqualSample();
    eq2.setId(121);
    System.out.println(eq1.equals(eq2));
  }

}
