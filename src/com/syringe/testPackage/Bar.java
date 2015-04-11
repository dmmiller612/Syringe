package com.syringe.testPackage;


import com.syringe.inject.Syringe;
import com.syringe.inject.annotations.Injectable;
import com.syringe.inject.annotations.Module;

@Module
public class Bar {

    @Injectable
    private Foo foo;

    @Injectable
    private Jar jar;

    public void doSomeFoo(){
        foo.sing();
        foo.yell();
    }

    public void doSomeJar(){
        jar.ball();
        jar.bright();
    }

    public static void main(String[] args) throws Exception {
        Syringe syringe = Syringe.registerPackage("com.syringe.testPackage");
        Bar bar = syringe.getClassInstance(Bar.class);
        bar.doSomeFoo();
        bar.doSomeJar();
    }

}
