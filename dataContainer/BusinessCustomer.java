package dataContainer;

public class BusinessCustomer extends Customer{
	
	private Person personCode;
	private String name;
	private Address address;
	
	public BusinessCustomer(String customerCode,String type, String name,Person personCode, Address address){
		super(customerCode,type,name,personCode);
		this.personCode=personCode;
		this.name=name;
		this.address=address;
	}

	public Person getContact() {
		return personCode;
	}
	public void setContact(Person personCode) {
		this.personCode = personCode;
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
