package com.oreland.ioc.test;

import com.oreland.ioc.core.Component;

@Component
public class TestClass {

    private String first;
    private String second;
    private String third;

    @Override
    public String toString() {
        return "TestClass{" +
                "first='" + first + '\'' +
                ", second='" + second + '\'' +
                '}';
    }
}
