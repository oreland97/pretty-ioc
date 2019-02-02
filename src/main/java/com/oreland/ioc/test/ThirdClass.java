package com.oreland.ioc.test;

import com.oreland.ioc.core.Component;

@Component
public class ThirdClass {

    private Long id;

    @Override
    public String toString() {
        return "ThirdClass{" +
                "id=" + id +
                '}';
    }
}
