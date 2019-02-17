package com.oreland.ioc.core;

import com.google.common.reflect.ClassPath;
import com.oreland.ioc.core.annotations.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class ComponentDefinitionReader {

    public List<Class> scan(Class main) {
        ClassLoader classLoader = ComponentDefinitionReader.class.getClassLoader();
        String packageName = main.getPackage().getName();

        List<Class> existingClasses = new ArrayList<>();
        try {
            existingClasses = ClassPath.from(classLoader).getTopLevelClassesRecursive(packageName).stream()
                    .map(ClassPath.ClassInfo::load)
                    .filter(c -> c.isAnnotationPresent(Component.class))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sortByDependencies(existingClasses);
    }

    private List<Class> sortByDependencies(List<Class> existingClasses) {
        List<Edge> edges = new DefinitionsSorter().prepareEdges(existingClasses);
        Graph graph = new Graph(edges, existingClasses.size());
        List<Integer> sorted = TopologicalSort.doTopologicalSort(graph, existingClasses.size());

        List<Class> definitions = new ArrayList<>();

        for (Integer integer : sorted) {
            definitions.add(existingClasses.get(integer));
        }

        return definitions;
    }
}
