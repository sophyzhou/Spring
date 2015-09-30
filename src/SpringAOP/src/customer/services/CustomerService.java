package customer.services;

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