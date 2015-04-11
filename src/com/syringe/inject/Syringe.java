package com.syringe.inject;

import com.syringe.inject.register.RegisterPackage;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class Syringe {

    private Map<Class, List<Field>> packageContents;

    private Syringe(Map<Class, List<Field>> packageContents){
        this.packageContents = packageContents;
    }

    public static Syringe registerPackage(String dependencyPackage) throws Exception{
        return new Syringe(RegisterPackage.setPackage(dependencyPackage));
    }

    public <T> T getClassInstance(Class<T> incomingClass) throws Exception{
        T instance = incomingClass.newInstance();
        InstanceInjection.inject(instance, packageContents);
        return instance;
    }
}
