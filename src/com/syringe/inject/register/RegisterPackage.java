package com.syringe.inject.register;

import com.syringe.inject.annotations.Injectable;
import com.syringe.inject.annotations.Module;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RegisterPackage {
    
    public static Map<Class, List<Field>> setPackage(String dependencyPackage) throws Exception{
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

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
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

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
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
