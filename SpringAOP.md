#Spring AOP
Spring AOP(Aspect-oriented programming) framework is used to modularize **cross-cutting concerns** in aspects. These cross-cutting concerns are conceptually separate from the application's business logic. It is just an interceptor to intercept some processes, for example, when a method is execute, Spring AOP can hijack the executing method and add extra functionality before or after the method execution.
In Spring AOP, 4 type of advices are supported:

 - Before advice  
 - After returning advice
 - After throwing advice
 - Around advice   


Spring supports the @AspectJ annotation style approach and the schema-based approach to implement custom aspects.  We will use XML schema-based approach to introduce AOP using four example.

Simple Example:
```
//class file
package customer;
public class CustomerService {
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public void printName() {
		System.out.println("Customer name : " + this.name);
	}
	public void printThrowException() {
		throw new IllegalArgumentException();
	}
}

//Bean file
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"	xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">
	<bean id="customerService" class="customer.CustomerService">
		<property name="name" value="UC Boulder" />
	</bean>
</beans>

//Main Function
public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "Spring-Customer.xml" });
		CustomerService cust = (CustomerService) appContext.getBean("customerService");		System.out.println("*************************");
		cust.printName();		System.out.println("*************************");
		try {
			cust.printThrowException();
		} catch (Exception e) {
		}
	}
```
Now attach Spring AOP advice to the above class:

###Before advice



Create a class which implements MethodBeforeAdvice interface.   

```
import java.lang.reflect.Method;
import org.springframework.aop.MethodBeforeAdvice;
public class HijackBeforeMethod implements MethodBeforeAdvice
{
	@Override
	public void before(Method method, Object[] args, Object target)
		throws Throwable {
	        System.out.println("HijackBeforeMethod : Before method hijacked!");
	}
}
```
In bean configuration file (Spring-Customer.xml), create a bean for HijackBeforeMethod class , and a new proxy object named "customerServiceProxy".  
```
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"	xsi:schemaLocation="http://www.springframework.org/schema/beans        http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">
	<bean id="customerService" class="customer.services.CustomerService">
		<property name="name" value="UC Boulder" />		
	</bean>
	<bean id="hijackBeforeMethodBean" class="HijackBeforeMethod" />
	<bean id="customerServiceProxy"                  class="org.springframework.aop.framework.ProxyFactoryBean">
		<property name="target" ref="customerService" />
		<property name="interceptorNames">
			<list>				<value>hijackBeforeMethodBean</value>
			</list>
		</property>
	</bean>
</beans>
```

 - ‘target’ – Define which bean you want to hijack.
 - ‘interceptorNames’ – Define which class (advice) you want to apply on this proxy /target object.   

Run it again, now we get the new customerServiceProxybean instead of the original customerService bean.
```
*************************
HijackBeforeMethod : Before method hijacked!
Customer name : UC Boulder
*************************
HijackBeforeMethod : Before method hijacked!
```
###After returning advice

It will execute after the method is returned a result. The method to implement is the same as before advice, just create a class which implements AfterReturningAdvice interface.
```
import java.lang.reflect.Method;
import org.springframework.aop.AfterReturningAdvice;

public class HijackAfterMethod implements AfterReturningAdvice
{
	@Override
	public void afterReturning(Object returnValue, Method method,
		Object[] args, Object target) throws Throwable {
	        System.out.println("HijackAfterMethod : After method hijacked!");
	}
}
```
The output will be
```
*************************
Customer name : UC Boulder
HijackAfterMethod : After method hijacked!
*************************
```
### After throwing advice
It will execute after the method throws an exception. We need to create a class which implements ThrowsAdvice interface, and create a afterThrowing method to hijack the IllegalArgumentException exception.
```
import org.springframework.aop.ThrowsAdvice;

public class HijackBeforeMethod implements ThrowsAdvice {
	public void afterThrowing(IllegalArgumentException e) throws Throwable {
		System.out.println("HijackThrowException : Throw exception hijacked!");
	}
}
```
The result should be:
```
*************************
Customer name : UC Boulder
*************************
HijackThrowException : Throw exception hijacked!
```
###Around advice  
It combines all three advices above, and execute during method execution. In implementation, creating a class which implements MethodInterceptor interface. We have to call the “methodInvocation.proceed();” to proceed on the original method execution, else the original method will not execute.
```
import org.springframework.aop.ThrowsAdvice;
import java.util.Arrays;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class HijackAroundMethod implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {

		System.out.println("HijackAroundMethod : Before method hijacked!");

		try {
			Object result = methodInvocation.proceed();

			System.out.println("HijackAroundMethod : Before after hijacked!");
			return result;
		} catch (IllegalArgumentException e) {
			// same with ThrowsAdvice
			System.out.println("HijackAroundMethod : Throw exception hijacked!");
			throw e;
		}
	}
}
```
The result is
```
*************************
HijackAroundMethod : Before method hijacked!
Customer name : UC Boulder
HijackAroundMethod : Before after hijacked!
*************************
HijackAroundMethod : Before method hijacked!
HijackAroundMethod : Throw exception hijacked!
```
Most of the Spring developers are just implements the ‘Around advice ‘, since it can apply all the advice type.


[HOME](README.md)  
  
[NEXT](Spring_mvc.md)
