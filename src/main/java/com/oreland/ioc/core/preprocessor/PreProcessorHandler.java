package com.oreland.ioc.core.preprocessor;

import java.util.ArrayList;
import java.util.List;

public class PreProcessorHandler {

    private static PreProcessorHandler ourInstance = new PreProcessorHandler();

    public static PreProcessorHandler getInstance() {
        return ourInstance;
    }

    private PreProcessorHandler() {
    }

    private List<PreProcessor> registry = new ArrayList<>();

    public boolean register(PreProcessor processor) {
        return registry.add(processor);
    }

    public Class invoke(Class definition) {
        for (PreProcessor preProcessor : registry) {
            preProcessor.process(definition);
        }
        return definition;
    }
}
