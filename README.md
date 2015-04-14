# Syringe
Very lightweight dependency injection framework for Java

Syringe is a very lightweight, easy to use dependency injection framework. Without comments and annotations, it is ~115 lines
of code. In order for your class to be injectable, you need to first add the @Module annotation to your class. i.e:

```java
@Module
public class SomeClass {
}
```

And to inject it, use the Injectable annotation:
```java
@Injectable
private SomeClass someClass;
```

To register your package, so that you can call Injectable modules, you will need to use the static method 
Syringe.registerPackage()
```java
Syringe syringe = Syringe.registerPackage("com.syringe.testPackage");
```

Then to call SomeClass: 

```java
SomeClass someClass = syringe.getClassInstance(SomeClass.class);
```

This will inject all dependencies the class has. For a working example, you can checkout the testPackage in the repository.
