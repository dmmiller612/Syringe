package com.syringe.testPackage;


import com.syringe.inject.annotations.Injectable;
import com.syringe.inject.annotations.Module;

@Module
public class Jar {

    @Injectable
    private Star star;

    public void ball(){
        System.out.println("I like Ball jars");
    }

    public void bright(){
        System.out.println("Right before star");
        star.shine();
    }
}
