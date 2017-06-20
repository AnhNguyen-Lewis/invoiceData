package dataContainer;

public class Products {
	
	private String code;
	private String serviceType;
	


public Products(String code, String serviceType){
	super();
	this.code=code;
	this.serviceType=serviceType;
	
}
public String getcode() {
	return code;
}
public void setcode(String code) {
	this.code = code;
}
public String getsetviceType(){
	return serviceType;
}
public void setserviceType(String serviceType){
	this.serviceType=serviceType;
	}

}