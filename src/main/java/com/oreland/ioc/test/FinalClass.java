package com.oreland.ioc.test;

import com.oreland.ioc.core.Component;

@Component
public class FinalClass {

    private TestClass testClass;
    private ThirdClass thirdClass;

    public FinalClass(TestClass testClass, ThirdClass thirdClass) {
        this.testClass = testClass;
        this.thirdClass = thirdClass;
    }

    @Override
    public String toString() {
        return "FinalClass{" +
                "testClass=" + testClass +
                ", thirdClass=" + thirdClass +
                '}';
    }
}
