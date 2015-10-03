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