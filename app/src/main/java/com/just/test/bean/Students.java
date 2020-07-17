package com.just.test.bean;

/**
 * Created by Administrator on 2017/1/21.
 */

public class Students {

    public int id;
    public String name;
    public String sex;
    public int age;
    public String address;

    public Students() {

    }

    public Students(int id, String name, String sex, int age, String address) {
        super();
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Students{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
