package com.oreland.ioc.core;

import com.oreland.ioc.core.exceptions.InjectionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ComponentFactory {

    private Map<String, Object> components = new HashMap<>();

    public Map<String, Object> inject(Map<String, Class> definitions) throws InvocationTargetException, InstantiationException, InjectionException, IllegalAccessException {
        Collection<Class> classes = definitions.values();
        for (Class cl : classes) {
            for (Constructor constructor : cl.getDeclaredConstructors()) {
                if (constructor.getAnnotation(Inject.class) != null) {
                    injectByConstructor(constructor, definitions);
                }
            }
        }

        return this.components;
    }

    public void injectByConstructor(Constructor constr, Map<String, Class> definitions) throws InjectionException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Object> constrParams = new ArrayList<>();
        for (Class paramType : constr.getParameterTypes()) {
            Class def = definitions.get(paramType.getSimpleName());
            if (def != null) {
                try {
                    constrParams.add(def.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (constrParams.isEmpty()) throw new InjectionException("Can't inject using constructor");
        if (constrParams.size() != constr.getParameterCount()) throw new InjectionException("You can't inject component without \"@Component\" annotation");

        Object o = constr.newInstance(constrParams.toArray());
        components.put(o.getClass().getSimpleName(), o);
    }

    public void injectByField(List<Class> classes) {
    }

    public void injectByMathod(List<Class> classes) {
    }
}
