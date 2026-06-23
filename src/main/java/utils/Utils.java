package main.java.utils;

import annotation.ControllerAnnotation;

import java.io.File;
import java.net.URL;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static List<Class<?>> scanClasses(String packageName) throws Exception {

        List<Class<?>> classes = new ArrayList<>();

        String path = packageName.replace('.', '/');

        ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();

        URL resource = classLoader.getResource(path);

        if (resource == null) {
            throw new Exception("Package introuvable : " + packageName);
        }

        File directory = new File(resource.getFile());

        File[] files = directory.listFiles();

        if (files == null) {
            return classes;
        }

        for (File file : files) {

            if (file.getName().endsWith(".class")) {

                String className =
                        packageName + "."
                        + file.getName().substring(0,
                                file.getName().length() - 6);

                classes.add(Class.forName(className));
            }
        }

        return classes;
    }

    public static List<String> scanAnnotation(
            String packageName,
            Class<? extends Annotation> annotationClass)
            throws Exception {

        List<String> result = new ArrayList<>();

        List<Class<?>> classes = scanClasses(packageName);

        for (Class<?> clazz : classes) {

            if (clazz.isAnnotationPresent(annotationClass)) {
                result.add(clazz.getName());
            }
        }

        return result;
    }
}