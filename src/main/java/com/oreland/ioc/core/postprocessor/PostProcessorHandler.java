package com.oreland.ioc.core.postprocessor;

import java.util.ArrayList;
import java.util.List;

public class PostProcessorHandler {

    private static PostProcessorHandler ourInstance = new PostProcessorHandler();

    public static PostProcessorHandler getInstance() {
        return ourInstance;
    }

    private PostProcessorHandler() {
    }

    private List<PostProcessor> registry = new ArrayList<>();

    public boolean register(PostProcessor processor) {
        return registry.add(processor);
    }

    public Object invoke(Object component) {
        for (PostProcessor postProcessor : registry) {
            postProcessor.process(component);
        }
        return component;
    }
}
