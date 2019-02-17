package com.oreland.ioc.core.property;

import com.oreland.ioc.core.postprocessor.PostProcessor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyReaderPostProcessor implements PostProcessor {

    private Map<String, String> properties;

    @Override
    public Object process(Object component) {
        Class componentClass = component.getClass();

        Stream.of(componentClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Property.class))
                .forEach(f -> {
                    if (properties == null) properties = readPropertyFile();

                    Property annotation = f.getAnnotation(Property.class);
                    String valueFromProperties = properties.get(annotation.value());

                    if (valueFromProperties != null) {
                        f.setAccessible(true);
                        try {
                            f.set(component, valueFromProperties);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return component;
    }

    private Map<String, String> readPropertyFile() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/application.properties");
        Map<String, String> params = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {

            params = reader.lines()
                    .map(l -> l.split("="))
                    .collect(Collectors.toMap(k -> k[0], v -> v[1]));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return params;
    }
}
