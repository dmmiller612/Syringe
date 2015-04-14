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

    @Injectable
    private Star star;

    public void doSomeFoo(){
        foo.sing();
        foo.yell();
    }

    public void doSomeJar(){
        jar.ball();
        jar.bright();
    }

    public void doSomeStar(){
        star.doSomeJar();
        star.doSomeBarJar();
    }

    public static void main(String[] args) throws Exception {
        Syringe syringe = Syringe.registerPackage("com.syringe.testPackage");
        Bar bar = syringe.getClassInstance(Bar.class);
        bar.doSomeFoo();
        bar.doSomeJar();
        bar.doSomeStar();

        Jar jar = syringe.getClassInstance(Jar.class);
        jar.ball();
        jar.bright();
    }

}
