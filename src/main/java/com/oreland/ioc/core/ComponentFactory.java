package com.oreland.ioc.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ComponentFactory {

    private Map<String, Object> components = new HashMap<>();

    public Map<String, Object> inject(List<Class> definitions) {

        definitions.stream()
                .flatMap(cl -> Stream.of(cl.getDeclaredConstructors()))
                .forEach(this::injectByConstructor);

        return this.components;
    }

    private void injectByConstructor(Constructor constructor) {
        Class[] parameterTypes = constructor.getParameterTypes();
        if (parameterTypes.length == 0) {
            createComponent(constructor);
        } else {
            createComponent(constructor, Stream.of(parameterTypes)
                    .map(t -> components.get(t.getSimpleName())).toArray());
        }

    }

    private void createComponent(Constructor constructor) {
        try {
            Object created = constructor.newInstance();
            components.put(created.getClass().getSimpleName(), created);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void createComponent(Constructor constructor, Object[] params) {
        try {
            Object created = constructor.newInstance(params);
            components.put(created.getClass().getSimpleName(), created);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
