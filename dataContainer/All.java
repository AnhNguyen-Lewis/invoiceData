package dataContainer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

import com.mwc.ext.DataBaseInfo;
import com.mwc.ext.InvoiceData;

public class All {
	
	private static MyArrayList<Products> products = new MyArrayList<Products>();//
    private static MyArrayList<Equipments> equipments = new MyArrayList<Equipments>();//
    private static MyArrayList<Services> services = new MyArrayList<Services>();//
    private static MyArrayList<Consultations> consultants = new MyArrayList<Consultations>();//
    private static MyArrayList<Person> persons = new MyArrayList<Person>();//
    private static MyArrayList<Customer> customers = new MyArrayList<Customer>();//
    private static MyArrayList<BusinessCustomer> customerB = new MyArrayList<BusinessCustomer>();//
    private static MyArrayList<ResidenceCustomer> customerR = new MyArrayList<ResidenceCustomer>();//
    private static MyArrayList<Invoice> invoices = new MyArrayList<Invoice>();



	public static void main(String[] args) {
		try {

		Class.forName("com.mysql.jdbc.Driver");
	    Connection conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
	    PreparedStatement p1 = null;
	    PreparedStatement p2 = null;
	    ResultSet rs = null;
	    ResultSet res = null;
	    ResultSet rs1 = null;
	    String equipmentQuery = "SELECT Products.ProductCode, Products.ProductName, PricePerUnit FROM Equipments Join Products On Products.ProductID = Equipments.ProductID;";
	    String serviceQuery = "SELECT Products.ProductCode, Products.ProductName, ActivationFee, AnnualFee FROM Services Join Products On Products.ProductID = Services.ProductID;";
	    String consultantQuery = "SELECT Products.ProductCode, TechCode, Products.ProductName, HourlyFee FROM Consultations Join Products On Products.ProductID = Consultations.ProductID;";
	    String personsQuery = "SELECT PersonCode, FirstName, LastName, Address.Street, Address.City, Address.State, Address.Zip, Address.Country FROM Person Join Address On Person.AddressID = Address.AddressID;";
	    String emailQuery = "Select Email From Emails Join Person On Emails.PersonID = Person.PersonID Where PersonCode = ?";
	    String customersQuery = "SELECT CustomerCode, TypeID, CustomerName, Address.Street, Address.City, Address.State, Address.Zip, Address.Country, Person.PersonCode FROM Customer Join Address On Customer.AddressID = Address.AddressID Join Person On Person.PersonID = Customer.PersonID;";
	    String invoiceQuery = "SELECT InvoiceID, InvoiceCode, Customer.CustomerCode, Person.PersonCode FROM Invoice Join Customer On Customer.CustomerID = Invoice.CustomerID Join Person On Person.PersonID = Invoice.PersonID;";
	    String invoiceProdQuery = "SELECT Invoice.InvoiceCode, Products.ProductCode, Quantity FROM InvoiceProducts Join Invoice On Invoice.InvoiceID = InvoiceProducts.InvoiceID Join Products On Products.ProductId = InvoiceProducts.ProductID;";
	    try {
	        p1 = conn.prepareStatement(personsQuery);
	        rs = p1.executeQuery();
	        while (rs.next()) {
	        	
	        
	       
	            String personCode = rs.getString("PersonCode");
	         	
	            String firstName = rs.getString("FirstName");
	            String lastName = rs.getString("LastName");
	            String street = rs.getString("Address.Street");
	            String city = rs.getString("Address.City");
	            String state = rs.getString("Address.State");
	            String zip = rs.getString("Address.Zip");
	            String country = rs.getString("Address.Country");
//	            
	            Address add = new Address(street, city, state, zip, country);
	            p1 = conn.prepareStatement(emailQuery);
	            p1.setString(1, personCode);
		        rs1 = p1.executeQuery();
		        
		        ArrayList<String> emailList = new ArrayList<String>();
		        while (rs1.next()){
		        	emailList.add(rs1.getString("Email"));	
		        	
		        }
	            
	            if(emailList != null) {
	            	
	            	//ArrayList<String> emails = new ArrayList<String>(Arrays.asList(emailList));
	                Person person = new Person(personCode, firstName, lastName, add, emailList);
	                persons.add(person);
	            } else {
	            	//System.out.println("hello2");
	                Person person = new Person(personCode, firstName, lastName, add, null);
	                persons.add(person);
	            }
		        
	        }
	        p1 = conn.prepareStatement(consultantQuery);
	        rs = p1.executeQuery();
	        while(rs.next()) {
	            String productCode = rs.getString("Products.ProductCode");
	            String consultantCode = rs.getString("TechCode");
	            String productName = rs.getString("Products.ProductName");
	            double pricePerHour = rs.getDouble("HourlyFee");
	            for (Person person : persons) {
	                if(consultantCode.equals(person.getPersonCode())) {
	                    Consultations consultant = new Consultations(productCode,"C", productName, person, pricePerHour);
	                    consultants.add(consultant);
	                    products.add(consultant);
	                    break;
	                }
	            }
	        }
	        p1 = conn.prepareStatement(equipmentQuery);
	        rs = p1.executeQuery();
	        while(rs.next()) {
	            String productCode = rs.getString("Products.ProductCode");
	            String productName = rs.getString("Products.ProductName");
	            double pricePerHour = rs.getDouble("PricePerUnit");
	            Equipments equipment = new Equipments(pricePerHour, productCode, productName, "E");
	            equipments.add(equipment);
	            products.add(equipment);
	        }
	        p1 = conn.prepareStatement(serviceQuery);
	        rs = p1.executeQuery();
	        while(rs.next()) {
	            String productCode = rs.getString("Products.ProductCode");
	            String productName = rs.getString("Products.ProductName");
	            double serviceFee = rs.getDouble("ActivationFee");
	            double annualFee = rs.getDouble("AnnualFee");
	            Services service = new Services(productCode,"S", productName, serviceFee, annualFee);
	            services.add(service);
	            products.add(service);
	        }
	        p1 = conn.prepareStatement(customersQuery);
	        rs = p1.executeQuery();
	        	
	        while (rs.next()) {
	        	
	            String customerCode = rs.getString("CustomerCode");
	            String customerType = rs.getString("TypeID");
	            String organizationName = rs.getString("CustomerName");
	            String street = rs.getString("Address.Street");
	            String city = rs.getString("Address.City");
	            String state = rs.getString("Address.State");
	            String zip = rs.getString("Address.Zip");
	            String country = rs.getString("Address.Country");
	            String primaryContact = rs.getString("Person.PersonCode");
	        
	            Address add = new Address(street, city, state, zip, country);
	            Person person = null;
	            for(int i=0;i<persons.size();i++){
	            	person = persons.get(i);
	            
	          
	                if(primaryContact.equalsIgnoreCase(person.getPersonCode())) {
	                    if(customerType.equalsIgnoreCase("R")) {
	                        ResidenceCustomer customer = new ResidenceCustomer(customerCode, customerType, organizationName, person,add);
	                        customerR.add(customer);
	                        customers.add(customer);
	                        
	                    } else if(customerType.equalsIgnoreCase("B")) {
	                        BusinessCustomer customerBusiness = new BusinessCustomer(customerCode,  customerType,organizationName, person, add);
	                        customerB.add(customerBusiness);
	                        customers.add(customerBusiness);
	                        
	                    }
	                    break;
	                }
	            }
	        } 
	        p1 = conn.prepareStatement(invoiceQuery);
	        rs = p1.executeQuery();
	        
	        while (rs.next()) {
	        	
	            String invoiceCode = rs.getString("InvoiceCode");
	            String customerCode = rs.getString("Customer.CustomerCode");
	            String salesPerson = rs.getString("Person.PersonCode");
	            
	            MyArrayList<Products> productList = new MyArrayList<Products>();
	            MyArrayList<Double> quantity = new MyArrayList<Double>();
	            Person person = null;
	            Customer customer = null;
	            p2 = conn.prepareStatement(invoiceProdQuery);
	            res = p2.executeQuery();
	            while(res.next()) {
	            	
	                String invoiceC = res.getString("Invoice.InvoiceCode");
	                if(invoiceCode.equals(invoiceC)) {
	                    String productCode = res.getString("Products.ProductCode");
	                    quantity.add(res.getDouble("Quantity"));
	                    for(int i=0; i< products.size();i++){
	                    //for(Products product : products) {
	                        if(productCode.equalsIgnoreCase(products.get(i).getcode())) {
	                            productList.add(products.get(i));
	                            break;
	                        }
	                    }
	                    //for (Customer c : customers) {
	                    Customer c = null;
	                    Person p = null;
	                   
	                    for(int i=0;i<customers.size();i++){
	                    	c = customers.get(i);
//	                    	
	                        if(customerCode.equalsIgnoreCase(c.getcode())) {
	                        	for(int j = 0; j<persons.size();j++){
	                         
	                                p = persons.get(j);
	                        
	                        		if(salesPerson.equalsIgnoreCase(p.getPersonCode())){
	                        			
	                                    person = p;
	                                    customer = c;
	                                    break;
	                        		}
	                            }
	                           break;
	                        }
	                    }
	                }
	            }
//	            
	         
	            Invoice invoice = new Invoice(invoiceCode, customer, person, productList, quantity);
	            invoices.add(invoice);
	        }
	    } catch (SQLException se) {
	        se.printStackTrace();
	    }
	    
	    final String HEADER = "%-12s%-35s%-30s%10s%13s%14s%13s";
		final String DATA = "%-12s%-35s%-30s$%10.2f      $%7.2f    $%7.2f  $%10.2f%n";
		System.out.println("======================");
		System.out.println("INVOICE SUMMARY REPORT");
		System.out.println("======================");
		
		System.out.printf(HEADER, "Invoice","Customer","Salesperon","Subtotal","Fees","Taxes","Total\n");
		
		double runningTotal = 0;
        double runningSubtotal = 0;
        double runningFees = 0;
        double runningTaxes = 0;
		for(int i =0;i<invoices.size();i++){
		System.out.printf(DATA, invoices.get(i).getInvoiceCode(),invoices.get(i).getCustomerCode().getName(),
				invoices.get(i).getSalesPerson().getLastName() + "," + invoices.get(i).getSalesPerson().getFirstName(),
				invoices.get(i).getSubtotalTotal(), invoices.get(i).getFees(), invoices.get(i).getTaxes(), invoices.get(i).getTotal());
		
		runningSubtotal = invoices.get(i).getSubtotalTotal() + runningSubtotal;
        runningFees = invoices.get(i).getFees() + runningFees;
        runningTaxes = invoices.get(i).getTaxes() + runningTaxes;
        runningTotal = invoices.get(i).getTotal() + runningTotal;
	}
		System.out.println("===========================================================================================================================================");
		System.out.println(String.format("TOTALS                                                                             $%-10.2f$%-10.2f$%-10.2f$%-10.2f\n\n\n\n", runningSubtotal, runningFees, runningTaxes, runningTotal));
		

		for(int i =0;i<invoices.size();i++){
			
		final String INVOICE = "%-10s%-60s$%-10.2f$%-10.2f%-10.2f%n";
		System.out.println("======================");
		System.out.println("INVOICE DETAILS REPORT");
		System.out.println("======================");
		
		System.out.println("----------------------");
		System.out.println("Invoice " + invoices.get(i).getInvoiceCode());

		System.out.println("----------------------");
		System.out.println("Salesperson: " + invoices.get(i).getSalesPerson().getLastName() + "," + invoices.get(i).getSalesPerson().getFirstName());
		System.out.println("Type of business:"+ invoices.get(i).getCustomerCode().getType());
		System.out.println("Customer:");
		System.out.println( invoices.get(i).getCustomerCode().getName());
		System.out.println( invoices.get(i).getCustomerCode().getContact().getLastName() + "," + invoices.get(i).getCustomerCode().getContact().getFirstName() +"\n"+invoices.get(i).getCustomerCode().getAddress().getStreet());
		System.out.println( invoices.get(i).getCustomerCode().getAddress().getCity() + "," + invoices.get(i).getCustomerCode().getAddress().getState()+ invoices.get(i).getCustomerCode().getAddress().getZip()
			+ " " + invoices.get(i).getCustomerCode().getAddress().getCountry());
		System.out.println("---------------------------------------------------------");
		System.out.printf("%-10s%-60s%-13s%-10s%n", "Code","Item","Fees","SubTotal");

			
		invoices.get(i).getSubtotalPrint();
		
		System.out.printf("%112s%n", "==========================================");
//		System.out.printf("%-70s$%-9.2f$%-12.2f%-12.2f$%-12.2f%n", "SUB-TOTALS",invoices.get(i).getSubTotal(),invoices.get(i).getTaxes(), invoices.get(i).getInvoiceFees(), invoices.get(i).getTotal());
//		System.out.printf("%-103s$%-6.2f%n", "COMPLIANCE FEE", invoices.get(i).ComplianceFee());
//		System.out.printf("%-103s$%-8.2f%n%n%n", "TOTAL",invoices.get(i).getTotal().invoice.getComplianceFee());
		
	}
//		System.out.printf(INVOICE, invoices.get(i).getcode(), invoices.get(i).getname()+ " (" + entry.getValue() + " units at $" + e.getpricePerUnit() + "/unit)",e.getpricePerUnit()*entry.getValue(),(e.getpricePerUnit() * entry.getValue() * .07), invoice.getFees(e), invoice.getTotal(e, entry.getValue()) );
//		System.out.printf(INVOICE, invoices.get(i).getcode(), invoices.get(i).getname()+ " (" + entry.getValue() + " hours at $" + c.gethourlyFee() + "/hr)",c.gethourlyFee() * entry.getValue(),(c.gethourlyFee() * entry.getValue() * .0425), invoice.getFees(c), invoice.getTotal(c, entry.getValue()) );
//		System.out.printf(INVOICE, invoices.get(i).getcode(), invoices.get(i).getname()+ " (" + entry.getValue() + " days at $" + s.getannualFee() + "/yr)",s.getannualFee() * entry.getValue() / 365,((s.getannualFee() * entry.getValue() / 365) * .0425), invoice.getFees(s), invoice.getTotal(s, entry.getValue()) );
	
//		System.out.printf("%112s%n", "==========================================");
//		System.out.printf("%-70s$%-9.2f$%-12.2f%-12.2f$%-12.2f%n", "SUB-TOTALS",invoice.getSubTotal(),invoice.getTaxes(), invoice.getInvoiceFees(), invoice.getTotal());
//		System.out.printf("%-103s$%-6.2f%n", "COMPLIANCE FEE", invoice.getComplianceFee());
//		System.out.printf("%-103s$%-8.2f%n%n%n", "TOTAL",invoice.getTotal()+invoice.getComplianceFee())

		
//		

    } catch(Exception e) {
        e.printStackTrace();
    }

}
}


