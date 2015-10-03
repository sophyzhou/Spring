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