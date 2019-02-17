package com.oreland.ioc.test;

import com.oreland.ioc.core.Component;
import com.oreland.ioc.core.property.Property;

@Component
public class ThirdClass {

    @Property(value = "param.first")
    private String title;

    @Override
    public String toString() {
        return "ThirdClass{" +
                "title='" + title + '\'' +
                '}';
    }
}
