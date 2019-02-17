package com.oreland.ioc.test;

import com.oreland.ioc.core.postprocessor.PostProcessor;

public class PostProcTest implements PostProcessor {
    @Override
    public Object process(Object component) {
        System.out.println("Post Processor TEST");
        return component;
    }
}
