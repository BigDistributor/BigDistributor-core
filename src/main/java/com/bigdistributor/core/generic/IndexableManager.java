package com.bigdistributor.plugin.generic;

import app.BigDistributorApp;
import com.bigdistributor.core.app.IndexableClass;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class IndexableManager {
    private static final Set<Class<?>> indexableClasses = new Reflections("").getTypesAnnotatedWith(IndexableClass.class);

    public static Set<Class<?>> getClassesForAnnotation(Class<? extends Annotation> annotationClass) {
        return indexableClasses.stream().filter(c -> c.getAnnotation(annotationClass) != null).collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        Set<Class<?>> cls = getClassesForAnnotation(BigDistributorApp.class);
        Iterator<Class<?>> itr = cls.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next().getName());
        }
    }

}
