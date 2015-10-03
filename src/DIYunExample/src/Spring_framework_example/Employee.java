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
