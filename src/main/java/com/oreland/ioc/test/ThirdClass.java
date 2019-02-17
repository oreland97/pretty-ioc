package com.oreland.ioc.test;

import com.oreland.ioc.core.annotations.Component;

@Component
public class ThirdClass {

    private String title = "I'm a Third class!";

    @Override
    public String toString() {
        return "ThirdClass{" +
                "title='" + title + '\'' +
                '}';
    }
}
