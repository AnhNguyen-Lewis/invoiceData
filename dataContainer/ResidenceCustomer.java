package dataContainer;

public class ResidenceCustomer extends Customer{

	private Person someOne;
	private String name;
	private Address address;
	
	public ResidenceCustomer(String customerCode,String type, String name,Person someOne, Address address){
		super(customerCode, type,name,someOne);

		this.someOne=someOne;
		this.name=name;
		this.address=address;
	}

	public Person getPerson() {
		return someOne;
	}
	public void setContact(Person someOne) {
		this.someOne = someOne;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

}
