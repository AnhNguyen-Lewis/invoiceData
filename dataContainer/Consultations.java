package dataContainer;

public class Consultations extends Products {

	private String name;
	private Person techCode;
	private double hourlyFee;

	public Consultations(String code, String serviceType,String name, Person techCode, double hourlyFee){
		super(code,serviceType);
		this.name=name;
		this.techCode=techCode;
		this.hourlyFee=hourlyFee;
	}
	
public String getname(){
	return name;
}
public void setname(String name){
	this.name=name;
}
public Person gettechCode(){
	return techCode;
}
public void settechCode(Person techCode){
	this.techCode=techCode;
}
public double gethourlyFee(){
	return hourlyFee;
}
public void sethourlyFee(double hourlyFee){
	this.hourlyFee=hourlyFee;
}
}