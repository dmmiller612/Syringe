# Syringe
Very lightweight dependency injection framework for Java

Copyright [2015] [Derek Miller]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

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
Syringe syringe = Syringe.registerPackage("testPackage");
```

Then to call SomeClass: 

```java
SomeClass someClass = syringe.getClassInstance(SomeClass.class);
```

This will inject all dependencies the class has. A working example will be linked soon.
