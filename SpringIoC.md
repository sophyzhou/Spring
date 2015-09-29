The Spring IoC(Inversion of Control) container is at the core of the Spring Framework. The container will create the objects,wire them together, configure them, and manage their complete lifecycle from creation till destruction.The Spring container uses dependency injection to manage the Spring beans which make up an application. The Spring IoC container makes use of Java POJO classs and configuration metadata to produce a fully configured and executable system. The following dagram is a high-level view of how Spring works.

![Alt text](/picture/Ioc Container.png)  
The picture is from http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/beans.html

The Spring IoC container is made up with two basic packages:
  - BeanFactory
  - ApplicationContext  

The BeanFactory provides the configuration framework and basic functionality and the ApplicationContext adds more enterprise-specific functionality.The ApplicationContext is a complete superset of the BeanFactory.
