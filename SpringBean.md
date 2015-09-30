#Spring Bean 
------------------------

A bean is an object that is instantiated, assembled and managed by a Spring IoC container. A Spring IoC container manages one or more beans. These beans are created with the configuration metadata that you supply to the container. The configuration metadata include three informations:

 - How to create a bean
 - Bean's lifecycle
 - Bean's dependencies


Those information translate to a set of properties that make up each bean definition which we will discuss in the following contents.

[<i class="icon-refresh"></i> class](#class)  

[<i class="icon-refresh"></i> name](#name)  

 [<i class="icon-refresh"></i> scope](#scope)  

  [<i class="icon-refresh"></i> constructor arguments](#constructor_arguments)  

  [<i class="icon-refresh"></i> properties](#properties)  

  [<i class="icon-refresh"></i> autowiring mode](#autowiring_mode)  

  [<i class="icon-refresh"></i> lazy-initialization method](#lazy-initialization_method)  

 [<i class="icon-refresh"></i> initialization method](#initialization)  

 [<i class="icon-refresh"></i> destruction method](#destruction)  

 There are three important methods to provide configuration metadata to the Spring Container:


 - XML based configuration file
 - Annotation-based configuration
 - Java-based configuration


In this presentation, we mainly use the first one XML based configuration file for introduction.


Class
----------
A bean definition is a recipe for creating objects. The container looks at the recipe for a named bean when asked and uses the configuration metadata encapsulated by that bean definition to create an actual object. Based on this definition, the class attribute is mandatory.  The object to be instantiated in the **class** attribute of the < **bean/**> element.
```

<!-- A simple bean definition -->
< bean id="..." class="...">
<!-- collaborators and configuration for this bean go here -->
< /bean>
```
Name
----------
Every bean has one or more identifiers. These identifiers must be unique within the container that hosts the bean. A bean usually has only one identifier, but if it requires more than one, the extra ones can be considered aliases. In XML based configuration metadata, the id and/or name attributes to specify the bean identifier.
Specifying all aliases where the bean is actually defined is desirable to introduce an alias for a bean that is defined elsewhere. This is commonly the case in large systems where configuration is split amongst each subsystem, each subsystem having its own set of object definitions. In XML-based configuration metadata, you can use the <**alias/**> element to accomplish this.
```
<alias name="bean1" alias="bean2"/>
```
In this case, a bean in the same container named bean1 is referred to bean2.


Scope
---------
When defining a <**bean**> in Spring, you have the option of declaring a scope for that bean.  The Spring Framework supports five scopes, three of which are available only if you use a web-aware ApplicationContext.
Scope     | Description
-------- | ---
 singleton | (Default)Scopes the bean definition to a single instance per Spring IoC container
prototype  | Scopes a single bean definition to any number of object instances.
request     | Scopes a single bean definition to the lifecycle of a single HTTP request; Only valid in the context of a web-aware Spring ApplicationContext.
session   |   Scopes a single bean definition to the lifecycle of an HTTP Session. Only valid in the context of a web-aware Spring ApplicationContext.
global-session  |Scopes a bean definition to a global HTTP session. Only valid in the context of a web-aware Spring ApplicationContext.

```
// A bean definition with singleton scope
<bean id="..." class="..." scope="singleton">
    <!-- collaborators and configuration for this bean go here -->
</bean>
```
```
// A bean definition with prototype scope
<bean id="..." class="..." scope="prototype">
   <!-- collaborators and configuration for this bean go here -->
</bean>
```
lazy-initialization_method
--------------------------------------
By default, ApplicationContext implementations eagerly create and configure all singleton beans as part of the initialization process. When this behavior is not desirable, we can prevent pre-instantiation of a singleton bean by marking the bean definition as lazy-initialized. A lazy-initialized bean tells the IoC container to create a bean instance when it is first requested, rather than at startup.
In XML, this method is controlled by *lazy-init* attribute on the < bean /> element:
```
<bean id="..." class="..." lazy-init="true"/>
```
When the preceding configuration is consumed by an ApplicationContext, this bean is not eagerly pre-instantiated when the ApplicationContext is starting up.
However, when a lazy-initialized bean is a dependency of a singleton bean that is not lazy-initialized, the ApplicationContext creates the lazy-initialized bean at startup, because it must satisfy the singleton’s dependencies. The lazy-initialized bean is injected into a singleton bean elsewhere that is not lazy-initialized.

Initialization
------------------
The *org.springframework.beans.factory.InitializingBean* interface allows a bean to perform initialization work after all necessary properties on the bean have been set by the container. The InitializingBean interface specifies a single method:
```
void afterPropertiesSet() throws Exception;
```
So you can simply implement above interface:
```
public class Examplebean implements InitializingBean {
   public void afterPropertiesSet() {
      // do some initialization work
   }
}
```
In the XML-based configuration metadata, we can use the init-method attribute to specify the name of the method that has a void no-argument signature:
```
<bean id="exampleBean"
         class="examples.ExampleBean" init-method="init"/>
```
 The class definition is as follow:

```
public class ExampleBean {
   public void init() {
      // do some initialization work
   }
}
```

Destruction
-------------------
The *org.springframework.beans.factory.DisposableBean* interface specifies a single method:
```
void destroy() throws Exception;
```
The implementation of the interface can be done inside destroy() method as follows:
```
public class ExampleBean implements DisposableBean {
   public void destroy() {
      // do some destruction work
   }
}
```
In the case of XML-based configuration metadata, we can use the destroy method attribute to specify the name of the method that has a void no-argument signature:
```
<bean id="exampleBean"
         class="examples.ExampleBean" destroy-method="destroy"/>
```
Following is the class definition:
```
public class ExampleBean {
   public void destroy() {
      // do some destruction work
   }
}
```
If there are too many beans having initialization and/or destroy methods with the same name, we don't need to declare init-method and destroy-method on each individual bean. Instead framework provides the flexibility to configure such situation using **default-init-method** and **default-destroy-method** attributes on the < beans > element as follows:
```
<beans default-init-method="init" default-destroy-method="destroy">
    <bean id="..." class="...">
        <property name="..." ref="..." />
    </bean>
</beans>
```
So far, we have learned how to define a bean in XML--based configuration metadata. Now let's take an example to make all the contents above together.
```
// this is the class file
package com.tutorialspoint;
public class HelloWorld {
   private String message;
   public void setMessage(String message){
      this.message  = message;
   }
   public void getMessage(){
      System.out.println("The Message : " + message);
   }
   public void init(){
      System.out.println("Bean is going through init.");
   }
   public void destroy(){
      System.out.println("Bean is going through destroy.");
   }
}
```
```
//this is the BeanPostProcessor file
package com.tutorialspoint;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.BeansException;
public class InitHelloWorld implements BeanPostProcessor {
   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      System.out.println("This will be executed before Initialization ");
      return bean;
   }
   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      System.out.println("This will be executed after Initialization ");
      return bean;
   }
}
```
```
//this is the main function
package com.tutorialspoint;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
   public static void main(String[] args) {
      AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
      obj.setMessage("I am the first object");
      obj.getMessage();
      HelloWorld obj2 = (HelloWorld) context.getBean("helloWorld");
      obj2.getMessage();
      context.registerShutdownHook();
   }
}
```
```
//this is the Bean XML file
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.2.xsd">

   <bean id="helloWorld" class="com.tutorialspoint.HelloWorld" scope="singleton"
       init-method="init" destroy-method="destroy">
       <property name="message" value="Default Value"/>
   </bean>

   <bean class="com.tutorialspoint.InitHelloWorld" />

</beans>
```
In the above XML file, the id of bean is helloWorld, and the related class is HelloWorld. The scope of the bean is singleton. The initation method is init() and the destroy method is destroy(). The result of the above code is:

>This will be executed before Initialization  

>Bean is going through init.  

>This will be executed after Initialization  

>The Message : I am the first object  

>The Message : I am the first object  

>Bean is going through destroy.

The class of HelloWorld can be just instantialized once.
If the scope changes to prototype,  we will got:
>This will be executed before Initialization  

>Bean is going through init.  

>This will be executed after Initialization  

>The Message : I am the first object  

>This will be executed before Initialization  

>Bean is going through init.  

>This will be executed after Initialization  

>The Message : Default Value

The bean can have two instances when using prototype.
