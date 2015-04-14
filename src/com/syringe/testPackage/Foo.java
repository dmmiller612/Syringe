package com.syringe.testPackage;

import com.syringe.inject.annotations.Module;

@Module
public class Foo {

    public void sing(){
        System.out.println("Foo singing");
    }

    public void yell(){
        System.out.println("Foo yelling!");
    }
}
