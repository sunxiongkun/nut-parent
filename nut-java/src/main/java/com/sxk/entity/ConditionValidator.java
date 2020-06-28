package com.sxk.entity;

public class ConditionValidator {

  /**
   * 条件表达式
   */
  private final String condition;
  private Boolean definition;
  private ThreadLocal<String> paramObtainLocal=new ThreadLocal<>();

  public ConditionValidator(String condition) {
    this.condition = condition;
  }

  public boolean formatValidate(String str) {
    if (condition != null) {
      paramObtainLocal.set(str);
      definition = condition.startsWith("ab");
      assert definition != null;
      return definition;
    } else {
      // 没有条件验证格式通过.
      return true;
    }
  }

  public boolean validate(String str) {
    return this.paramObtainLocal.get().equals(str);
  }

}
