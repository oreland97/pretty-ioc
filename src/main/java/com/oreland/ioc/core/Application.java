package com.oreland.ioc.core;

import com.oreland.ioc.core.postprocessor.PostProcessorHandler;
import com.oreland.ioc.core.property.PropertyReaderPostProcessor;

import java.util.List;

public class Application {

    public static void run(Class main, String[] args) {
        ComponentDefinitionReader definitionReader = new ComponentDefinitionReader();
        PostProcessorHandler.getInstance().register(new PropertyReaderPostProcessor());
        ComponentFactory factory = new ComponentFactory();
        List<Class> definitions = definitionReader.scan(main);
        factory.inject(definitions).forEach((k, v) -> System.out.println(k + " : " + v));
    }
}
