package com.oreland.ioc.test;

import com.oreland.ioc.core.Component;
import com.oreland.ioc.core.Inject;

@Component
public class NewClass {

    private TestClass test;
    private ThirdClass thirdClass;

    @Inject
    public NewClass(TestClass test, ThirdClass thirdClass) {
        this.test = test;
        this.thirdClass = thirdClass;
    }

    @Override
    public String toString() {
        return "NewClass{" +
                "test=" + test +
                ", thirdClass=" + thirdClass +
                '}';
    }
}
