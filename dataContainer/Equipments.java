package dataContainer;

import java.util.Map;

public class Equipments extends Products {

	private String name;
	private double pricePerUnit;

	public Equipments(double pricePerUnit,String code,String name,String serviceType){
		super(code,serviceType);
		this.name=name;
		this.pricePerUnit=pricePerUnit;
	}
	public String getname(){
		return name;
	}
	public void setname(String name){
		this.name=name;
	}
	public double getpricePerUnit(){
		return pricePerUnit;
	}
	public void setpricePerUnit(double pricePerUnit){
		this.pricePerUnit=pricePerUnit;
	}
}