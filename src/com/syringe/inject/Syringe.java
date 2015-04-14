package com.syringe.inject;

import com.syringe.inject.annotations.Injectable;
import com.syringe.inject.annotations.Module;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Syringe {

    private Map<Class, List<Field>> packageContents;
    private Map<Class, List<Field>> injectionMapper;
    private Map<Class, Object> injectManager = new HashMap<>();

    private Syringe(Map<Class, List<Field>> packageContents){
        this.packageContents = packageContents;
    }

    public static Syringe registerPackage(String dependencyPackage) throws Exception{
        return new Syringe(setPackage(dependencyPackage));
    }

    public <T> T getClassInstance(Class<T> incomingClass) throws Exception{
        T instance = incomingClass.newInstance();
        inject(instance, packageContents);
        return instance;
    }

    private void inject(Object instance, Map<Class, List<Field>> injectionMap) throws Exception{
        injectionMapper = injectionMap;
        injectRecursion(instance);
    }

    private void injectRecursion(Object instance) throws Exception{
        if (instance == null || injectionMapper.get(instance.getClass()).isEmpty()) return;
        for (Field field : injectionMapper.get(instance.getClass())){
            if (injectManager.get(field.getType()) != null){
                field.setAccessible(true);
                field.set(instance, injectManager.get(field.getType()));
            } else {
                field.setAccessible(true);
                Object newInstance = field.getType().newInstance();
                field.set(instance, newInstance);
                injectManager.put(field.getType(), newInstance);
                injectRecursion(field.get(instance));
            }
        }
    }

    private static Map<Class, List<Field>> setPackage(String dependencyPackage) throws Exception{
        if (dependencyPackage == null || dependencyPackage.equals("")) return null;
        Package wantedPackage = Package.getPackage(dependencyPackage);
        return findInjectableFields((findAnnotatedClasses(getClasses(wantedPackage.getName()))));
    }

    private static List<Class> findAnnotatedClasses(List<Class> classes){
        return classes.stream()
                .filter(clazz -> clazz != null && clazz.getDeclaredAnnotation(Module.class) != null)
                .collect(Collectors.toList());
    }

    private static Map<Class, List<Field>> findInjectableFields(List<Class> annotatedClasses){
        Map<Class, List<Field>> injectMapper = new HashMap<>();
        for (Class clazz : annotatedClasses){
            List<Field> tempFields = Arrays.asList(clazz.getDeclaredFields()).stream()
                    .filter(field -> field.isAnnotationPresent(Injectable.class) && annotatedClasses.contains(field.getType()))
                    .collect(Collectors.toList());

            injectMapper.put(clazz, tempFields);
        }
        return injectMapper;
    }

    private static List<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException, Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null){
            throw new ClassNotFoundException("Class isn't found");
        }
        //replaces the packagePath with slashes
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException, Exception {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                //adds class, substring removes .class
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
