package dataContainer;

import java.util.ArrayList;

public class Person {
	private String personCode;
	private String firstName;
	private String lastName;
	private Address address;
	private ArrayList<String>email;
	
	
	public Person(String personCode, String firstName, String lastName, Address address, ArrayList<String> email){
		super();
		this.personCode=personCode;
		this.firstName=firstName;
		this.lastName=lastName;
		this.address=address;
		this.email=email;
		
	}
	
	

	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public ArrayList<String> getEmail() {
		return email;
	}
	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}

	

}
//read in each text file and as a user buys the product it should output what he has purchased
// design data parsers