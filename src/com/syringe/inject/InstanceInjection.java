package com.syringe.inject;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class InstanceInjection {

    private static Map<Class, List<Field>> injectionMapper;

    public static void inject(Object instance, Map<Class, List<Field>> injectionMap) throws Exception{
        injectionMapper = injectionMap;
        injectRecursion(instance);
    }

    private static void injectRecursion(Object instance) throws Exception{
        if (instance == null || injectionMapper.get(instance.getClass()).isEmpty()) return;
        for (Field field : injectionMapper.get(instance.getClass())){
            field.setAccessible(true);
            field.set(instance, field.getType().newInstance());
            injectRecursion(field.get(instance));
        }
    }
}
