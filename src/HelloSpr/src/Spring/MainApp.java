package spring;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
   public static void main(String[] args) {

      AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

      HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
      //obj.setMessage("I am the first object");
      obj.getMessage();
      HelloWorld obj2 = (HelloWorld) context.getBean("helloWorld");
      obj2.getMessage();
      
      context.registerShutdownHook();
   }
}