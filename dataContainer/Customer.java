package dataContainer;

public class Customer {
	
	private String code;
	private String type;
	private String Name;
	private Person personCode;
	;

public Customer(String code, String type,String name,Person personCode){
	super();
	this.code=code;
	this.type=type;
	this.Name=name;
	this.personCode=personCode;
	
}
public String getcode() {
	return code;
}
public void setcode(String code) {
	this.code = code;
}
public String getType(){
	return type;
}
public void setType(String type){
	this.type=type;
	}
public String getName() {
	return Name;
}
public void setName(String name) {
	this.Name = name;
}
public Address getAddress() {
	// TODO Auto-generated method stub
	return null;
}
public Person getContact(){
	return personCode;
}


}
