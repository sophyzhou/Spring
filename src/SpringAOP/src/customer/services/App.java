package customer.services;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "Spring-Customer.xml" });

		CustomerService cust = (CustomerService) appContext.getBean("customerServiceProxy");

		System.out.println("*************************");
		cust.printName();

		System.out.println("*************************");
		try {
			cust.printThrowException();
		} catch (Exception e) {

		}

	}

}
