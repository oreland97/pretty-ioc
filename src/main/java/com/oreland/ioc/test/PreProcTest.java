package com.oreland.ioc.test;

import com.oreland.ioc.core.preprocessor.PreProcessor;

public class PreProcTest implements PreProcessor {
    @Override
    public Class process(Class definition) {
        System.out.println("Pre Processor TEST");
        return definition;
    }
}
