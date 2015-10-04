#Spring Overview

**Spring framework** is an open source Java platform released in 2003 and written by Rod Johnson, which provides comprehensive infrastructure support for developing robust Java applications. Millions of developers around the world use Spring Framework to create high performing, easily testable, reusable code.

The core features of the Spring Framework can be used in developing any Java application, but there are extensions for building web applications on top of the Java EE platform. Spring framework targets to make J2EE development easier to use and promote good programming practice by enabling a POJO-based programming model.


##Benefit of using Spring
* Works on POJOs. Hence easier for dependency injection/injection of test data.
* With the Dependency Injection approach, dependencies are explicit and evident in constructor or JavaBean properties.
* Enhances modularity. Provides more readable codes.
* Provides loose coupling between different modules.
* Effective in organizing the middle-tier applications.
* Flexible use of Dependency injection. Can be configured by XML based schema or annotation-based style.
* Supports declarative transaction, caching, validation and formatting.


##Architecture
Spring could potentially be a one-stop shop for all your enterprise applications, however, Spring is modular, allowing you to pick and choose which modules are applicable to you, without having to bring in the rest. Following section gives detail about all the modules available in Spring Framework.

The Spring Framework provides about 20 modules which can be used based on an application requirement.

![Alt text](/picture/spring framework overview.png)  
Reference:http://docs.spring.io/spring-framework/docs/3.0.x/reference/overview.html

###Core container
The *Core Container* consistes of Core, Bean, Context and Spring-Expression-Language(SpEL) modules.
***Core**:It provides the fundamental parts of the framework, including the IoC and Dependency Injection features.
***Beans**:It provides *BeanFactory* that is a sophisticated implementation of the factory pattern. It removes the need for programmatic singletons and allows decoupling the configuration and specification of dependencies from actual program logic.
***Context**:It builds on the solid base provided by the Core and Beans modules and it is a medium to access any objects defined and configured. The ApplicationContext interface is the focal point of the Context module.
***SpEL**:It provides a powerful Expression Language for querying and manipulating an object graph at runtime.


###Data Access/Integration
The *Data Access/Integration* layer consists of the JDBC, ORM, OXM, JMS, and Transaction modules.

The **JDBC**  module provides a JDBC-abstraction layer that removes the need to do tedious JDBC coding and parsing of database-vendor specific error codes.

The **ORM** module provides integration layers for popular object-relational mapping APIs, including JPA, JDO, and Hibernate. Using the ORM module you can use all of these O/R-mapping frameworks in combination with all of the other features Spring offers, such as the simple declarative transaction management feature mentioned previously.

The **OXM** module provides an abstraction layer that supports Object/XML mapping implementations such as JAXB, Castor, XMLBeans, JiBX and XStream.

The **JMS** module (Java Messaging Service) contains features for producing and consuming messages. Since Spring Framework 4.1, it provides integration with the spring-messaging module.

The **Transaction** module supports programmatic and declarative transaction management for classes that implement special interfaces and for all POJOs (Plain Old Java Objects).

###Web
The *Web* layer consists of the Web, Web-MVC, Web-Socket, and Web-Portlet modules.

The **Web** module provides basic web-oriented integration features such as multipart file upload functionality and the initialization of the IoC container using Servlet listeners and a web-oriented application context. It also contains an HTTP client and the web-related parts of Spring’s remoting support.

The **Web-MVC** module (also known as the Web-Servlet module) contains Spring’s model-view-controller (MVC) and REST Web Services implementation for web applications. Spring’s MVC framework provides a clean separation between domain model code and web forms and integrates with all of the other features of the Spring Framework.

The **Web-Socket** module provides support for WebSocket-based, two-way communication between client and server in web applications.

The **Web-Portlet module provides the MVC implementation to be used in a Portlet environment and mirrors the functionality of the spring-webmvc module.
