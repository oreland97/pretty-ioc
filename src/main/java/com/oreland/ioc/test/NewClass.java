package com.oreland.ioc.test;

import com.oreland.ioc.core.annotations.Component;

@Component
public class NewClass {

    private ThirdClass thirdClass;

    public NewClass(ThirdClass thirdClass) {
        this.thirdClass = thirdClass;
    }

    @Override
    public String toString() {
        return "NewClass{" +
                "thirdClass=" + thirdClass +
                '}';
    }
}
