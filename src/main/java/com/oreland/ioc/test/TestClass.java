package com.oreland.ioc.test;

import com.oreland.ioc.core.annotations.Component;

@Component
public class TestClass {

    private NewClass newClass;

    public TestClass(NewClass newClass) {
        this.newClass = newClass;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "newClass=" + newClass +
                '}';
    }
}
