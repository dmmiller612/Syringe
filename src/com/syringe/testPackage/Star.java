package com.syringe.testPackage;

import com.syringe.inject.annotations.Injectable;
import com.syringe.inject.annotations.Module;

@Module
public class Star {

    @Injectable
    private Jar jar;

    public void shine(){
        System.out.println("I am a star that shines so bright!");
    }

    public void doSomeJar(){
        System.out.println("I am skeptical");
        jar.ball();
    }
}
