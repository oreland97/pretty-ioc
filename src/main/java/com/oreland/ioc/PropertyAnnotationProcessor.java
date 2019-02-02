package com.oreland.ioc;

import com.google.common.reflect.ClassPath;
import com.oreland.ioc.test.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyAnnotationProcessor {

    public void process() {
        System.out.println("---------------------------");
        final String name = Main.class.getPackage().getName();

        ClassLoader cl = getClass().getClassLoader();
        try {
            ClassPath.from(cl).getTopLevelClassesRecursive(name).stream()
                    .flatMap(c -> Arrays.stream(c.load().getDeclaredFields()))
                    .filter(f -> f.isAnnotationPresent(Property.class))
                    .forEach(f -> {
                        Map<String, String> properties = readPropertyFile();
                        Property annotation = f.getAnnotation(Property.class);

                        String valueFromProperties = properties.get(annotation.value());

                        if (valueFromProperties != null) {
                            f.setAccessible(true);
                            try {

                                Object obj = f.getDeclaringClass().newInstance();
                                System.out.println("fffffffffffffffffffffffffffffffffff");
                                System.out.println(obj.getClass().getSimpleName());

                                f.set(obj, valueFromProperties);
                            } catch (IllegalAccessException | InstantiationException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            readPropertyFile().forEach((k, v) -> System.out.println(k + " : " + v));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Map<String, String> readPropertyFile() {
        InputStream resourceAsStream = PropertyAnnotationProcessor.class.getClassLoader().getResourceAsStream("application.properties");
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
