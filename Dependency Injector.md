#Dependency Injection


Dependency Injection (DI) is a design pattern that removes the dependency from the programming code so that it can be easy to manage and test the application. Dependency Injection makes our programming code loosely coupled. 

We are going to talk about dependency injection following the steps as below: 

 [<i class="icon-file"></i> Dependency Lookup](#dependency-lookup)  

 [<i class="icon-file"></i> Problems of dependency lookup](#problems-of-dependency-lookup)  

 [<i class="icon-file"></i> Dependency injection types and examples](#dependency-injection-types-and-examples)  

 [<i class="icon-file"></i>  Injecting Collection](#injecting-collection)  

  [<i class="icon-file"></i> Autowiring](#autowiring)  

 [<i class="icon-file"></i> Summary](#summary)  

 
 
  
 
##Dependency Lookup


To understand the DI better, Let's understand the Dependency Lookup (DL) first. The Dependency Lookup is an approach where we get the resource after demand. There can be various ways to get the resource. For example, consider we have an application which has a text editor component and we want to provide spell checking. The standard code would look something like this:
```
public class Employee{
   Address address;
   
   public Employee() {
      address = new Address();
   }
}
```
What we've done here is create a dependency between the Employee and the Address. Let's see the problem in this approach.

##Problems  of dependency lookup

There are mainly two problems of dependency lookup.

1. **tight coupling** The dependency lookup approach makes the code tightly coupled. If resource is changed, we need to perform a lot of modification in the code.
2. **Not easy for testing** This approach creates a lot of problems while testing the application especially in black box testing.

##Dependency injection types and examples 

The **Dependency Injection (DI)** is a design pattern that removes the dependency of the programs. In such case we provide the information from the external source such as XML file. It makes our code loosely coupled and easier for testing. 

In such case we write the code as: 

```
public class Employee{  
Address address;  

//By Constructor
Employee(Address address){  
this.address=address;  
}  

//By Setter method
public void setAddress(Address address){  
this.address=address;  
}  
  
}  
```
There are two ways to perform Dependency Injection in Spring framework. In this example, the first method we use is *constructor injection*. We have removed the total control from Employee and kept it somewhere else (ie. XML configuration file) and the dependency ( ie. class Address) is being injected into the class Employee through a Class Constructor. Thus flow of control has been "inverted" by DI because we have effectively delegated dependances to some external system.

The second method we use here is through *Setter Injection* of Employee class where we create Address instance and this instance will be used to call setter methods to initialize Employee's properties.

Next, we will talk more coding details of these two methods by giving examples.

###Constructor Injection
Constructor-based dependency injection is accomplished when the container invokes a class constructor with a number of arguments, each representing a dependency on other class. 

**Example:**

The following example shows a class Employee that only get dependency-injected with constructor injection.

We work Eclipse IDE and do the following steps to create a Spring application:

Step     | Description
-------- | ---
1|Create a java project with a name *DIYunExample* and create a package *Spring_framework_example* under the **src** folder in the created project. 
2|Add required Spring libraries using Add External JARs option. 
3|Create Java classes *Employee*, *Address* and *MainApp* under the  *Spring_framework_example*  package. 
4|Create Beans configuration file *Beans.xml* under the **src** folder.
5|The final step is to create the content of all the Java files and Bean Configuration file and run the application as explained below.

Here is the content of **Employee.java** file:

```
package Spring_framework_example;

public class Employee {
	   private Address address;

	   public Employee(Address address) {
	      System.out.println("Inside Employee constructor." );
	      this.address = address;
	   }
	   public void employeeAddress() {
	      address.checkAddress();
	   }
	}

```
Following is the content of another dependent class file **Address.java**:

```
package Spring_framework_example;

public class Address {
	   public Address(){
	      System.out.println("Inside Address constructor." );
	   }

	   public void checkAddress() {
	      System.out.println("Inside checkAddress method." );
	   }
	   
	}
```

Following is the content of the **MainApp.java** file:
```
package Spring_framework_example;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
   public static void main(String[] args) {
      AbstractApplicationContext context = 
             new ClassPathXmlApplicationContext("Beans.xml");

      Employee em = (Employee) context.getBean("employee");

      em.employeeAddress();
   }
}
```
Following is the configuration file **Beans.xml** which has configuration for the constructor-based injection:
```
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.2.xsd">

   <!-- Definition for employee bean -->
   <bean id="employee" class="Spring_framework_example.Employee">
      <constructor-arg ref="address"/>
   </bean>

   <!-- Definition for address bean -->
   <bean id="address" class="Spring_framework_example.Address">
   </bean>

</beans>
```

When we run the application,  this will print the following message:

>Inside Address constructor.
>Inside Employee constructor.
>Inside checkAddress method.


###Constructor arguments resolution:

There may be a ambiguity exist while passing arguments to the constructor in case there are more than one parameters. To resolve this ambiguity, the order in which the constructor arguments are defined in a bean definition is the order in which those arguments are supplied to the appropriate constructor. Consider the following class:

```
package x.y;

public class Foo {
   public Foo(Bar bar, Baz baz) {
      // ...
   }
}
```

The following configuration works fine:

```
<beans>
   <bean id="foo" class="x.y.Foo">
      <constructor-arg ref="bar"/>
      <constructor-arg ref="baz"/>
   </bean>

   <bean id="bar" class="x.y.Bar"/>
   <bean id="baz" class="x.y.Baz"/>
</beans>
```

Let us check one more case where we pass different types to the constructor. Consider the following class:

```
package x.y;

public class Foo {
   public Foo(int year, String name) {
      // ...
   }
}
```

The container can also use type matching with simple types if you explicitly specify the type of the constructor argument using the type attribute.  When we are passing a reference to an object, we use **ref** attribute of <**constructor-arg**> tag and if we are passing a value directly then we should use **value** attribute as shown below. For example:
```
<beans>

   <bean id="exampleBean" class="examples.ExampleBean">
      <constructor-arg type="int" value="2001"/>
      <constructor-arg type="java.lang.String" value="Zara"/>
   </bean>

</beans>
```

Finally and the best way to pass constructor arguments, use the **index** attribute to specify explicitly the index of constructor arguments. Here the index is 0 based. For example:
```
<beans>

   <bean id="exampleBean" class="examples.ExampleBean">
      <constructor-arg index="0" value="2001"/>
      <constructor-arg index="1" value="Zara"/>
   </bean>

</beans>
```

###Setter Injection
Setter-based dependency injection is accomplished by the container calling setter methods on beans after invoking a no-argument constructor or no-argument static factory method to instantiate bean.
 
**Example: **
The following example shows a class Employee that only get dependency-injected with setter injection.

We work Eclipse IDE and do the following steps to create a Spring application:

Step     | Description
-------- | ---
1|Create a java project with a name *DIYunExample_setter* and create a package *Spring_framework_example* under the **src** folder in the created project. 
2|Add required Spring libraries using Add External JARs option. 
3|Create Java classes *Employee*, *Address* and *MainApp* under the  *Spring_framework_example*  package. 
4|Create Beans configuration file *Beans.xml* under the **src** folder.
5|The final step is to create the content of all the Java files and Bean Configuration file and run the application as explained below.

Here is the content of **Employee.java** file:

```
package Spring_framework_example;


public class Employee {
   private Address address;

   // a setter method to inject the dependency.
   public void setAddress(Address address) {
      System.out.println("Inside setAddress method." );
      this.address = address;
   }
   // a getter method to return spellChecker
   public Address getAddress() {
      return address;
   }

   public void employeeAddress() {
      address.checkAddress();
   }
}

```

Following is the content of another dependent class file **Address.java**:
```
package Spring_framework_example;


public class Address {
   public Address(){
      System.out.println("Inside Address constructor." );
   }

   public void checkAddress() {
      System.out.println("Inside checkAddress method." );
   }
   
}
```

Following is the content of **MainApp.java** file:
```
package Spring_framework_example;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
   public static void main(String[] args) {
      AbstractApplicationContext context = 
             new ClassPathXmlApplicationContext("Beans.xml");

      Employee em = (Employee) context.getBean("employee");

      em.employeeAddress();
   }
}

```

Following is the configuration file **Beans.xml** which has configuration for the constructor-based injection:

```
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.2.xsd">

   <!-- Definition for employee bean -->
   <bean id="employee" class="Spring_framework_example.Employee">
      <property name="address" ref="address"/>
   </bean>

   <!-- Definition for address bean -->
   <bean id="address" class="Spring_framework_example.Address">
   </bean>

</beans>
```

  

###Constructor Injection vs Setter Injection.

There are many key differences between constructor injection and setter injection.

1. **Partial dependency**: can be injected using setter injection but it is not possible by constructor. Suppose there are 3 properties in a class, having 3 arg constructor and setters methods. In such case, if you want to pass information for only one property, it is possible by setter method only.  

2. **Overriding**: Setter injection overrides the constructor injection. If we use both constructor and setter injection, IOC container will use the setter injection.  

3. **Changes**: We can easily change the value by setter injection. It doesn't create a new bean instance always like constructor. So setter injection is flexible than constructor injection.  


##Injecting Collection
We  have seen how to configure primitive data type using value attribute and object references using ref attribute of the <**property**> tag in  Bean configuration file. These cases deal with passing singular value to a bean. Now what about if we want to pass plural values like Java Collection types List, Set, Map, and Properties. To handle the situation, Spring offers four types of collection configuration elements which are as follows:

<**list**>: This helps in wiring a list of values, allowing duplicates.
<**set**>: This helps in wiring a set of values but without any duplicates.
<**map**>: This can be used to inject a collection of name-value pairs where name and value can be of any type.
<**props**>: This can be used to inject a collection of name-value pairs where the name and value are both Strings.


##Autowiring 
The Spring container can autowire relationships between collaborating beans without using <**constructor-arg**> and <**property**> elements which helps cut down on the amount of XML configuration we write for a big Spring based application.

###Autowiring Modes:
The Spring container can autowire relationships between collaborating beans. Autowiring has the following advantages:

- Autowiring can significantly reduce the need to specify properties or constructor arguments. 
- Autowiring can update a configuration as your objects evolve. For example, if you need to add a dependency to a class, that dependency can be satisfied automatically without you needing to modify the configuration. Thus autowiring can be especially useful during development, without negating the option of switching to explicit wiring when the code base becomes more stable.

When using XML-based configuration metadata, you specify autowire mode for a bean definition with the autowire attribute of the <**bean**/> element. The autowiring functionality has four modes. You specify autowiring *per* bean and thus can choose which ones to autowire.


Mode     | Description
-------- | ---
no |It is the default autowiring mode. It means no autowiring bydefault.
byName|Autowiring by property name. Spring container looks at the properties of the beans on which autowire attribute is set to byName in the XML configuration file. It then tries to match and wire its properties with the beans defined by the same names in the configuration file.
byType|Autowiring by property datatype. Spring container looks at the properties of the beans on which autowire attribute is set to byType in the XML configuration file. It then tries to match and wire a property if its type matches with exactly one of the beans name in configuration file. If more than one such beans exists, a fatal exception is thrown.
Constructor|Similar to byType, but type applies to constructor arguments. If there is not exactly one bean of the constructor argument type in the container, a fatal error is raised.
autodetect|	It is deprecated since Spring 3.

###Autowiring Disadvantages:
Though, autowiring can significantly reduce the need to specify properties or constructor arguments, there are still some disadvantages we could not ignore:  

- **Overriding possibility**. We can still specify dependencies using <**constructor-arg**> and <**property**> settings which will always override autowiring.
- **Primitive data types**. We cannot autowire  simple properties such as primitives, Strings, and Classes.
- **Confusing nature**.  Autowiring is less exact than explicit wiring, so if possible prefer using explict wiring.





##Summary
Code is cleaner with the DI principle and decoupling is more effective when objects are provided with their dependencies. The object does not look up its dependencies, and does not know the location or class of the dependencies rather everything is taken care by the Spring Framework.





