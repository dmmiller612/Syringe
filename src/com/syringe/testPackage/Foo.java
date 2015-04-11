package com.syringe.testPackage;

import com.syringe.inject.annotations.Module;

@Module
public class Foo {

    public void sing(){
        System.out.println("FOO! FOO! FOO!");
    }

    public void yell(){
        System.out.println("AAAAHHHH FOOO");
    }
}
