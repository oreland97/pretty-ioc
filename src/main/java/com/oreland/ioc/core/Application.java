package com.oreland.ioc.core;

import com.oreland.ioc.core.exceptions.InjectionException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Application {

    public static void run(Class main, String[] args) {
        ComponentDefinitionReader definitionReader = new ComponentDefinitionReader();
        ComponentFactory factory = new ComponentFactory();
        Map<String, Class> definitions = definitionReader.getDefinitions(main);
        try {
            factory.inject(definitions).forEach((k, v) -> System.out.println(k + " : " + v));
        } catch (InvocationTargetException | InstantiationException | InjectionException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
