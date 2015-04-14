package com.syringe.testPackage;

import com.syringe.inject.annotations.Injectable;
import com.syringe.inject.annotations.Module;

@Module
public class Star {

    @Injectable
    private Jar jar;

    @Injectable
    private Bar bar;

    public void shine(){
        System.out.println("A shining star");
    }

    public void doSomeJar(){
        System.out.println("Right before jar (circular dependency");
        jar.ball();
    }

    public void doSomeBarJar(){
        System.out.println("More circular dependencies");
        bar.doSomeJar();
    }
}
