package hytch.com.lfp_base_recycleview_master.bean;

/**
 * Created by lfp on 16/10/10.
 * 数据bean
 */

public class Student {

  int id;
  String name;
  int age;

  public Student(int id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
