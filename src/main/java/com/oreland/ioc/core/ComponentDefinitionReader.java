package com.oreland.ioc.core;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class ComponentDefinitionReader {

    private Map<String, Class> definitions;

    public Map<String, Class> getDefinitions(Class main) {
        if (definitions != null) return definitions;
        return scan(main);
    }

    private Map<String, Class> scan(Class main) {
        ClassLoader classLoader = ComponentDefinitionReader.class.getClassLoader();
        String packageName = main.getPackage().getName();

        try {
            this.definitions = ClassPath.from(classLoader).getTopLevelClassesRecursive(packageName).stream()
                    .map(ClassPath.ClassInfo::load)
                    .filter(c -> c.isAnnotationPresent(Component.class))
                    .collect(Collectors.toMap(Class::getSimpleName, v -> v));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return definitions;
    }
}
