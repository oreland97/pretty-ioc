package com.oreland.ioc.core;

import com.google.common.reflect.ClassPath;
import com.oreland.ioc.core.exceptions.DefinitionReaderException;
import com.oreland.ioc.core.postprocessor.PostProcessor;
import com.oreland.ioc.core.postprocessor.PostProcessorHandler;
import com.oreland.ioc.core.preprocessor.PreProcessor;
import com.oreland.ioc.core.preprocessor.PreProcessorHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentDefinitionReader {

    private PreProcessorHandler preProcessorHandler = PreProcessorHandler.getInstance();
    private PostProcessorHandler postProcessorHandler = PostProcessorHandler.getInstance();

    public List<Class> scan(Class main) {
        ClassLoader classLoader = ComponentDefinitionReader.class.getClassLoader();
        String packageName = main.getPackage().getName();
        List<Class> existingClasses = new ArrayList<>();

        try {
            existingClasses = ClassPath.from(classLoader).getTopLevelClassesRecursive(packageName).stream()
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toList());

            existingClasses.stream()
                    .filter(PreProcessor.class::isAssignableFrom)
                    .map(this::createInstance)
                    .forEach(c -> preProcessorHandler.register((PreProcessor) c));

            existingClasses.stream()
                    .filter(PostProcessor.class::isAssignableFrom)
                    .map(this::createInstance)
                    .forEach(c -> postProcessorHandler.register((PostProcessor) c));

            existingClasses = existingClasses.stream()
                    .filter(c -> c.isAnnotationPresent(Component.class))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sortByDependencies(existingClasses);
    }

    private Object createInstance(Class aClass) {
        try {
            return aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DefinitionReaderException(e);
        }
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
