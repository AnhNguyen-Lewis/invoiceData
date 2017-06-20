
package dataContainer;

import java.util.LinkedHashMap;
import java.util.Map;

public class Invoice {
	

	private double subtotal; 
	private double equipmentSubtotal;
	private double licenseSubtotal;
	private double consultantSubtotal;
	private double taxes;
	private double fees;
    private String invoiceCode;
    private Customer customerCode;
    private Person salesPerson;
    private MyArrayList<Products> productCodes = new MyArrayList<Products>();
    private MyArrayList<Double> quantity = new MyArrayList<Double>();

    public Invoice(String invoiceCode, Customer customerCode, Person salesPerson, MyArrayList<Products> productCodes, MyArrayList<Double> quantity) {
        this.invoiceCode = invoiceCode;
        this.customerCode = customerCode;
        this.salesPerson = salesPerson;
        this.productCodes = productCodes;
        
        this.quantity = quantity;
	
    }
    public void testInfo(){
    	System.out.println(this.toString());
    }
    
    public String getInvoiceCode() {
        return invoiceCode;
    }

    public Customer getCustomerCode() {
        return customerCode;
    }

    public Person getSalesPerson() {
        return salesPerson;
    }

    public MyArrayList<Double> getQuantity() {
        return quantity;
    }
    
    public MyArrayList<Products> getProducts() {
        return productCodes;
    }
    
    public double getSubtotalTotal() {
    	subtotal = 0;
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Equipments) {
    			Equipments e = (Equipments) productCodes.get(i);
    			subtotal += e.getpricePerUnit() * quantity.get(i);
    		} else if(productCodes.get(i) instanceof Services) {
    			Services s = (Services) productCodes.get(i);
				
                subtotal += s.getannualFee() * quantity.get(i) / 365;
            } else if(productCodes.get(i) instanceof Consultations) {
            	Consultations c = (Consultations) productCodes.get(i);
		
                subtotal += c.gethourlyFee() * quantity.get(i);
            }
        }
		return subtotal;
    }

    public void getSubtotalPrint() {
    	subtotal = 0;
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Equipments) {
    			Equipments e = (Equipments) productCodes.get(i);
    			subtotal = e.getpricePerUnit() * quantity.get(i);
    			fees = 0;
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", e.getcode(), e.getname(), " (" + quantity.get(i) + " units @ " + e.getpricePerUnit() + "/unit)", fees, subtotal));
    		} else if(productCodes.get(i) instanceof Services) {
    			Services s = (Services) productCodes.get(i);
                subtotal = s.getannualFee() * quantity.get(i) / 365;
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", s.getcode(), s.getname(), " (" + quantity.get(i) + " days @ " + s.getannualFee() + "/year)", s.getactivationFee(), subtotal));
            } else if(productCodes.get(i) instanceof Consultations) {
            	Consultations c = (Consultations) productCodes.get(i);
                subtotal = c.gethourlyFee() * quantity.get(i);
                fees = 150;
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", c.getcode(), c.getname(), " (" + quantity.get(i) + " hours @ " + c.gethourlyFee() + "/hour)", fees, subtotal));
            }
        }
    }
    
    public double getComplienceFee() {
    	double complianceFee = 0;
    	if(customerCode instanceof BusinessCustomer) {
    		complianceFee = 150;
    	}
    	return complianceFee;
    }
    
    public double getFees() {
    	if(customerCode instanceof BusinessCustomer) {
    		fees = 0;
    		for(int i=0; i<productCodes.size(); i++) {
    			if(productCodes.get(i) instanceof Services) {
    				Services s = (Services) productCodes.get(i);
    				fees = s.getactivationFee() + fees;
    			} else if(productCodes.get(i) instanceof Consultations) {
    				fees = 150 + fees;
    			}
    		}
    	} else if(customerCode instanceof ResidenceCustomer) {
    		fees = 125;
    		for(int i=0; i<productCodes.size(); i++) {
    			if(productCodes.get(i) instanceof Services) {
    				Services s = (Services) productCodes.get(i);
    				fees = s.getactivationFee() + fees + fees;
    			} else if(productCodes.get(i) instanceof Consultations) {
    				fees = 150 + fees;
    			}
    		}
    	}
    	return fees;
    }

    public double getTaxes() {
    	taxes = 0;
    	if(customerCode instanceof BusinessCustomer){
    		
    		for(int i=0; i<productCodes.size(); i++) {
    			
    			if(productCodes.get(i) instanceof Equipments) {
    				
    				Equipments e = (Equipments) productCodes.get(i);
    				subtotal = e.getpricePerUnit() * quantity.get(i);
    				taxes = subtotal * .07 + taxes;
    			} else if(productCodes.get(i) instanceof Services) {
    				
    				Services s = (Services) productCodes.get(i);
    				subtotal = s.getannualFee() * quantity.get(i) / 365;
    				taxes = subtotal * .0425 + taxes;
    			} else if(productCodes.get(i) instanceof Consultations) {
    				Consultations c = (Consultations) productCodes.get(i);
    				
                    subtotal = c.gethourlyFee() * quantity.get(i);
    				taxes = subtotal * .0425 + taxes;
    			}
    		}
    	} else if(customerCode instanceof ResidenceCustomer) {
    		taxes = 0;
    	}
    	return taxes;
    }

    public double getTotal() {
        double total = getSubtotalTotal() + getFees() + getTaxes();
        return total;
    }

    public void getEquipmentSubtotal() {
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Equipments) {
    			Equipments e = (Equipments) productCodes.get(i);
    			equipmentSubtotal = e.getpricePerUnit() * quantity.get(i);
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", e.getcode(), e.getname(), " (" + quantity.get(i) + " units @ " + e.getpricePerUnit() + "/unit)", fees, equipmentSubtotal));
    			break;
    		}
    	}
    }

    public void getConsultationSubtotal() {
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Consultations) {
    			Consultations c = (Consultations) productCodes.get(i);
    			consultantSubtotal = c.gethourlyFee() * quantity.get(i);
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", c.getcode(), c.getname(), " (" + quantity.get(i) + " hours @ " + c.gethourlyFee() + "/hour)", fees, consultantSubtotal));
    			break;
    		}
    	}
    }

    public void getServiceSutotal() {
    	for(int i=0; i<productCodes.size(); i++) {
    		if(productCodes.get(i) instanceof Services) {
    			Services s = (Services) productCodes.get(i);
    			licenseSubtotal = s.getannualFee() * quantity.get(i) / 365;
                System.out.println(String.format("%-12s%-35s%-35s$%-10.2f$%-20.2f", s.getcode(), s.getname(), " (" + quantity.get(i) + " days @ " + s.getannualFee() + "/year)", fees, licenseSubtotal));
    			break;
    		}
    	}
    }
}
