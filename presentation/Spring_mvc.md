
#Spring MVC Framework 

The Spring web MVC framework provides model-view-controller architecture and ready components that can be used to develop flexible and loosely coupled web applications. The MVC pattern results in separating the different aspects of the application (input logic, business logic, and UI logic), while providing a loose coupling between these elements.

- The **Model** encapsulates the application data and in general they will consist of POJO.

- The **View** is responsible for rendering the model data and in general it generates HTML output that the client's browser can interpret.

- The **Controller** is responsible for processing user requests and building appropriate model and passes it to the view for rendering.

We are going to talk about Spring MVC Framework as the following steps:  

 [<i class="icon-file"></i>DispatcherServlet](#dispatcherservlet)
  [<i class="icon-file"></i>Configuration](#configuration)
   [<i class="icon-file"></i>Controller](#controller)
      [<i class="icon-file"></i>JSP Views](#jsp-views)



 
##DispatcherServlet
The Spring MVC framework is designed around a DispatcherServlet that works as the front controller and manage the flow of the spring MVC application. The HTTP request processing workflow of the Spring Web MVC *DispatcherServlet* is illustrated in the following diagram:

![Alt text](/picture/dispatcherservlet.png)  
Reference: http://docs.spring.io/spring/docs/2.5.6/reference/mvc.html  


Following is the sequence of events corresponding to an incoming HTTP request to DispatcherServlet:

- As displayed in the figure, all the incoming request is intercepted by the DispatcherServlet that works as the front controller. 
- After receiving an HTTP request, *DispatcherServlet* consults the *HandlerMapping* to call the appropriate *Controller*.
- The *Controller* takes the request and calls the appropriate service methods based on used GET or POST method. The service method will set model data based on defined business logic and returns view name to the *DispatcherServlet*.
- The *DispatcherServlet* will take help from *ViewResolver* by checking the xml file to pickup the defined view for the request.
- Once view is finalized, The *DispatcherServlet* passes the model data to the view which is finally rendered on the browser.

All the above mentioned components ie. *HandlerMapping*, *Controller* and *ViewResolver* are parts of *WebApplicationContext* which is an extension of the plain *ApplicationContext* with some extra features necessary for web applications.

##Configuration

The request that *DispatcherServlet* is going to handle is needed to be mapped by using a URL in **Web.xml** file. Now we are giving an example to show declaration and mapping for **Spring_web_mvc** *DispatcherServlet*.

First of all, we build a JAVA dynamic web project named **Spring_web_mvc**, and create the java class and configuration files like below: 
![Alt text](/picture/spring_example.png)

```  
<web-app id="WebApp_ID" version="2.4"
   xmlns="http://java.sun.com/xml/ns/j2ee" 
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee 
   http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">

   <display-name>Spring MVC Application</display-name>

   <servlet>
      <servlet-name>Spring_web_mvc</servlet-name>
      <servlet-class>
         org.springframework.web.servlet.DispatcherServlet
      </servlet-class>
      <load-on-startup>1</load-on-startup>
   </servlet>

   <servlet-mapping>
      <servlet-name>Spring_web_mvc</servlet-name>
      <url-pattern>/</url-pattern>
   </servlet-mapping>
 
</web-app>
```

The **web.xml** file will be kept WebContent/WEB-INF directory of  the web application. Upon initialization of **Spring_web_mvc** DispatcherServlet, the framework will try to load the application context from a file named [servlet-name]-servlet.xml located in the application's WebContent/WEB-INF directory. In this case our file will be **Spring_web_mvc-servlet.xml**.

Next, <**servlet-mapping**> tag indicates what URLs will be handled by the which DispatcherServlet.   

Now, let us check the required configuration for **Spring_web_mvc-servlet-servlet.xml** file, placed in web application's WebContent/WEB-INF directory:

```
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:context="http://www.springframework.org/schema/context"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="
   http://www.springframework.org/schema/beans     
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/context 
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">

   <context:component-scan base-package="Spring_framework_example" />
   <bean id="viewResolver"
        class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass"
            value="org.springframework.web.servlet.view.JstlView" />
        <property name="prefix" value="/WEB-INF/jsp/" />
        <property name="suffix" value=".jsp" />
   </bean>
 
</beans>
```

Following are the important points about HelloWeb-servlet.xml file:

- The **[servlet-name]-servlet.xml** file will be used to create the beans defined, overriding the definitions of any beans defined with the same name in the global scope.

- The <**context:component-scan**...> tag will be use to activate Spring MVC annotation scanning capability which allows to make use of annotations like *@Controller* and *@RequestMapping* etc.

- The **InternalResourceViewResolver** will have rules defined to resolve the view names. As per the above defined rule, a logical view named **HelloWorld** is delegated to a view implementation located at */WEB-INF/jsp/HelloWorld.jsp.*

##Controller

DispatcherServlet delegates the request to the controllers to execute the functionality specific to it. The **@Controller** annotation indicates that a particular class serves the role of a controller. The **@RequestMapping** annotation is used to map a URL to either an entire class or a particular handler method.


```
package Spring_framework_example;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

@Controller
public class HelloWorld_Controller{
 
   //@RequestMapping(method = RequestMethod.GET)
	@RequestMapping("/HelloWorld")

   public ModelAndView helloWorld() {
	   
       String message = "Hello World, Spring 3.0!";
       return new ModelAndView("hello", "message", message);
   }

}

```

The **@Controller** annotation defines the class as a Spring MVC controller. Here, the  usage of **@RequestMapping** indicates that all handling methods on this controller are relative to the /HelloWorld path. 


##JSP Views

Spring MVC supports many types of views for different presentation technologies. These include - JSPs, HTML, PDF, Excel worksheets, XML, Velocity templates, XSLT, JSON, Atom and RSS feeds, JasperReports etc. But most commonly we use JSP templates written with JSTL. So let us write a simple hello view in /WEB-INF/jsp/HelloWorld.jsp:

```
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>Hello World</title>
</head>
<body>
   ${message}
</body>
</html>
```

Here **${message}** is the attribute which we have setup inside the Controller. 

[HOME](README.md)  
[NEXT](Reference.md)



