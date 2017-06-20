package dataContainer;

public class Services extends Products {
	private String name;
	private double activationFee;
	private double annualFee;

public Services(String code,String serviceType,String name, double activationFee, double annualFee){
	super(code,serviceType);
	this.name=name;
	this.activationFee=activationFee;
	this.annualFee=annualFee;
}
public String getname(){
	return name;
}
public void setname(String name){
	this.name=name;
}
public double getactivationFee(){
	return activationFee;
}
public void setactivationFee(double activationFee){
	this.activationFee=activationFee;
}
public double getannualFee(){
	return annualFee;
}
public void setannualFee(double annualFee){
	this.annualFee=annualFee;
	}

}