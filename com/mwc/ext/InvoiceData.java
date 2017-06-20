package com.mwc.ext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

public class InvoiceData {

/* NOTE: Donot change the package name or any of the method signatures.
 *  
 * There are 14 methods in total, all of which need to be completed as a 
 * bare minimum as part of the assignment.You can add additional methods 
 * for testing if you feel.
 * 
 * It is also recommended that you write a separate program to read
 * from the .dat files and test these methods to insert data into your 
 * database.
 * 
 * Donot forget to change your reports generation classes to read from 
 * your database instead of the .dat files.
 */


/**
 * Class containing all methods interacting with the database.
 */

	
	/**Method that removes every person record from the database. 
	 */
	public static void removeAllPersons() {
		Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String delete = "DELETE FROM Consultations;";
            ps = conn.prepareStatement(delete);
            ps.execute();
            String deleteQuery1 = "DELETE FROM Emails;";
            ps = conn.prepareStatement(deleteQuery1);
            ps.execute();
            String deleteQuery = "DELETE FROM Person;";
            ps = conn.prepareStatement(deleteQuery);
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
		
}
	
	/**Method to add a person record to the database with the provided data. 
	 * @throws SQLException 
	 */
	public static void addPerson(String personCode, String firstName, String lastName, 
			String street, String city, String state, String zip, String country) throws SQLException {

        Connection conn = DataBaseInfo.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		String addAddressQuery = "INSERT INTO Address (street,city,state,zip,country) VALUES (?,?,?,?,?)";
		String checkAddress = "SELECT AddressID FROM Address WHERE street = ? AND city = ? AND state = ? AND zip = ? AND country = ?";
		String addPersonQuery = "INSERT INTO Person (personCode,firstName,lastName,AddressID) VALUES (?,?,?,(SELECT AddressID FROM Address WHERE street = ? AND city = ? AND state = ? AND zip = ? AND country = ?))";
		try
		{
			ps = conn.prepareStatement(checkAddress);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			rs = ps.executeQuery();
			if(!(rs.next())){
				ps = conn.prepareStatement(addAddressQuery);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setString(3, state);
				ps.setString(4, zip);
				ps.setString(5, country);
				ps.executeUpdate();
				ps.close();
			}
			ps.close();
			rs.close();
			ps = conn.prepareStatement(addPersonQuery);
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);

			ps.setString(4, street);
			ps.setString(5, city);
			ps.setString(6, state);
			ps.setString(7, zip);
			ps.setString(8, country);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**Method to add an email record to the database with the associated personCode. 
	 */
	public static void addEmail(String personCode, String email) {
		Connection conn = DataBaseInfo.getConnection();
		PreparedStatement ps;
		
		String addEmailQuery = "INSERT INTO Emails (PersonID,Email) VALUES ((SELECT PersonID FROM Person WHERE PersonCode = ?),?)";
		try
		{
			ps = conn.prepareStatement(addEmailQuery);
			ps.setString(1, personCode);
			ps.setString(2, email);
			ps.executeUpdate();
			ps.close();

			conn.close();
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**Method that removes every customer record from the database. 
	 */
	public static void removeAllCustomers() {
		Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String deleteQuery = "DELETE FROM Customer;";
            ps = conn.prepareStatement(deleteQuery);
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
               if(conn != null) {
                   conn.close();
               }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**Method to add a customer record to the database with the provided data
	 */
	public static void addCustomer(String customerCode, String type, String primaryContactPersonCode, String name, 
			String street, String city, String state, String zip, String country) {
		Connection conn = DataBaseInfo.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		String addAddressQuery = "INSERT INTO Address (street,city,state,zip,country) VALUES (?,?,?,?,?)";
		String checkAddress = "SELECT AddressID FROM Address WHERE street = ? AND city = ? AND state = ? AND zip = ? AND country = ?";
		String addCustomerQuery = "INSERT INTO Customer (CustomerCode,TypeID,PersonID,CustomerName,AddressID) VALUES (?,?,(Select PersonID From Person Where PersonCode = ?),?,(SELECT AddressID FROM Address WHERE street = ? AND city = ? AND state = ? AND zip = ? AND country = ?))";
		try
		{
			ps = conn.prepareStatement(checkAddress);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			rs = ps.executeQuery();
			if(!(rs.next())){
				ps = conn.prepareStatement(addAddressQuery);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setString(3, state);
				ps.setString(4, zip);
				ps.setString(5, country);
				ps.executeUpdate();
				ps.close();
			}
			ps.close();
			rs.close();
			ps = conn.prepareStatement(addCustomerQuery);
			ps.setString(1, customerCode);
			ps.setString(2, type);
			ps.setString(3, primaryContactPersonCode);
			ps.setString(4, name);
			ps.setString(5, street);
			ps.setString(6, city);
			ps.setString(7, state);
			ps.setString(8, zip);
			ps.setString(9, country);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**Removes all product records from the database. 
	 */
	public static void removeAllProducts() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String deleteQuery = "DELETE FROM Equipments;";
            ps = conn.prepareStatement(deleteQuery);
            ps.executeUpdate();
            ps.close();
            String deleteQuery1 = "DELETE FROM Consultations;";
            ps = conn.prepareStatement(deleteQuery1);
            ps.executeUpdate();
            ps.close();
            String deleteQuery2 = "DELETE FROM Services;";
            ps = conn.prepareStatement(deleteQuery2);
            ps.executeUpdate();
            ps.close();
            String deleteQuery3 = "DELETE FROM Products;";
            ps = conn.prepareStatement(deleteQuery3);
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        
		}
	}
	
	/**Adds an equipment record to the database with the provided data.
	 */
	public static void addEquipment(String productCode, String name, Double pricePerUnit) {
		Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String productQuery= "INSERT INTO Products(ProductCode,ProductName) VALUES (?, ?)";
       
            ps = conn.prepareStatement(productQuery);
            ps.setString(1, productCode);
            ps.setString(2, name);
            ps.execute();
            String equipmentQuery = "INSERT INTO Equipments (ProductID, PricePerUnit) VALUES ((SELECT ProductID FROM Products WHERE ProductCode = ?), ?)";
            ps1 = conn.prepareStatement(equipmentQuery);
            ps1.setString(1, productCode);
            ps1.setDouble(2, pricePerUnit);
            ps1.executeUpdate();
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}

	
	/**Adds a service record to the database with the provided data.
	 */
	public static void addService(String productCode, String name, double activationFee, double annualFee) {
		Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String productQuery= "INSERT INTO Products(ProductCode,ProductName) VALUES (?, ?)";
            ps = conn.prepareStatement(productQuery);
            ps.setString(1, productCode);
            ps.setString(2, name);
            ps.execute();
            String licenseQuery = "INSERT INTO Services (ProductID, ActivationFee, AnnualFee) VALUES ((SELECT ProductID FROM Products WHERE ProductCode = ?), ?, ?);";
            ps1 = conn.prepareStatement(licenseQuery);
            ps1.setString(1, productCode);
            ps1.setDouble(2, activationFee);
            ps1.setDouble(3, annualFee);
            ps1.executeUpdate();
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**Adds an consultation record to the database with the provided data.
	 */
	public static void addConsultation(String productCode, String name, String consultantPersonCode, Double hourlyFee) {
		Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String productQuery= "INSERT INTO Products(ProductCode,ProductName) VALUES (?, ?)";
            ps = conn.prepareStatement(productQuery);
            ps.setString(1, productCode);
            ps.setString(2, name);
            ps.execute();
            String consultationQuery = "INSERT INTO Consultations (ProductID, TechCode, HourlyFee) VALUES ((SELECT ProductID FROM Products WHERE ProductCode = ?), ?, ?);";
            ps1 = conn.prepareStatement(consultationQuery);
            ps1.setString(1, productCode);
            ps1.setString(2, consultantPersonCode);
            ps1.setDouble(3, hourlyFee);
            ps1.executeUpdate();
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**Removes all invoice records from the database. 
	 */
	public static void removeAllInvoices() {
		Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String deleteInvoicePro = "DELETE FROM InvoiceProducts;";
            ps = conn.prepareStatement(deleteInvoicePro);
            ps.execute();
            String deleteQuery = "DELETE FROM Invoice;";
            ps = conn.prepareStatement(deleteQuery);
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
	}
	
	/**Adds an invoice record to the database with the given data.  
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String invoiceDate, String salesPersonCode) {

        Connection conn = DataBaseInfo.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		try
		{
			String checkSalesPerson= "SELECT PersonID FROM Person WHERE PersonCode = ?";		
			ps = conn.prepareStatement(checkSalesPerson);
			ps.setString(1, salesPersonCode);
			rs = ps.executeQuery();
			
//			if(!rs.next()){
//				salesPersonCode = null;
//			}
			ps.close();
			rs.close();
			
			String addInvoiceQuery = "INSERT INTO Invoice (InvoiceCode,CustomerID,PersonID,InvoiceDate) VALUES (?,(SELECT CustomerID FROM Customer WHERE CustomerCode = ?),(SELECT PersonID FROM Person WHERE PersonCode = ?),?)";
			ps = conn.prepareStatement(addInvoiceQuery);
			ps.setString(1, invoiceCode);
			ps.setString(2, customerCode);
			ps.setString(3, salesPersonCode);		
			ps.setString(4, invoiceDate);
			ps.executeUpdate();
			ps.close();
				conn.close();
		}catch (SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	/**Adds a particular equipment (corresponding to productCode to an 
	 * invoice corresponding to the provided invoiceCode with the given
	 * number of units)
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String productCode, int numUnits) {
		Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String equipmentInvoiceQuery = "INSERT INTO InvoiceProducts (InvoiceID, ProductID, Quantity) VALUES ((SELECT InvoiceID FROM Invoice WHERE InvoiceCode =?),(SELECT ProductID FROM Products WHERE ProductCode =?), ?)";
            ps = conn.prepareStatement(equipmentInvoiceQuery);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            ps.setDouble(3, numUnits);
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**Adds a particular service (corresponding to productCode to an 
	 * invoice corresponding to the provided invoiceCode with the given
	 * begin/end dates)
	 */
	public static void addServiceToInvoice(String invoiceCode, String productCode, String startDate, String endDate) {
		
		Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String equipmentInvoiceQuery = "INSERT INTO InvoiceProducts (InvoiceID, ProductID, Quantity) VALUES ((SELECT InvoiceID FROM Invoice WHERE InvoiceCode =?),(SELECT ProductID FROM Products WHERE ProductCode =?), ?)";
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            Date start = null;
//            try {
//                start = df.parse(startDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Date end = null;
//            try {
//                end = df.parse(endDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            int index2;
//			
			org.joda.time.format.DateTimeFormatter date = DateTimeFormat.forPattern("yyyy-MM-dd");
				
			
			
				
			DateTime begin = new DateTime(DateTime.parse(startDate, date));
			DateTime finish = new DateTime(DateTime.parse(endDate, date));
			int period = Days.daysBetween(begin, finish).getDays() +1 ;
            ps = conn.prepareStatement(equipmentInvoiceQuery);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            ps.setDouble(3, period);
            ps.executeUpdate();
//            String updateQuery = "UPDATE InvoiceProducts, Products SET InvoiceProducts.ProductID = Products.ProductID WHERE InvoiceProducts.ProductCode = Products.ProductCode;";
//            ps.executeUpdate(updateQuery);
//            updateQuery = "UPDATE InvoiceProducts, Invoices SET InvoiceProducts.InvoiceID = Invoices.InvoiceID WHERE InvoiceProducts.InvoiceCode = Invoices.InvoiceCode;";
//            ps.executeUpdate(updateQuery);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
		}

	
	public static void addConsultationToInvoice(String invoiceCode, String productCode, double numHours) {
		Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
            String consultationInvoiceQuery = "INSERT INTO InvoiceProducts (InvoiceID, ProductID, Quantity) VALUES ((SELECT InvoiceID FROM Invoice WHERE InvoiceCode =?),(SELECT ProductID FROM Products WHERE ProductCode =?), ?)";
            ps = conn.prepareStatement(consultationInvoiceQuery);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            ps.setDouble(3, numHours);
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    
		}
}

