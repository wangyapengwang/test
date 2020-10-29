package com.wyp.maven;

import java.util.Date;

public class Demo {

    private String name;
    private int age;
    private Date createDate;


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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Demo() {
        super();
    }

    public Demo(String name, Date createDate, int age) {
        this.name = name;
        this.createDate = createDate;
        this.age = age;
    }


    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                ", createDate=" + createDate +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.setName("");
        demo.setCreateDate(new Date());
        demo.setAge(0);
    }
}
