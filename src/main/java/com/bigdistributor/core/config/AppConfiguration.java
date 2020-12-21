package com.bigdistributor.core.config;

public class AppConfiguration {

    private String test;
    private int age;

    public AppConfiguration(){}
    public AppConfiguration(String test, int age) {
        this.test = test;
        this.age = age;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
