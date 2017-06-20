package com.mwc.ext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBaseInfo {
	public static final String url = "jdbc:mysql://cse.unl.edu/jhitchcock";
	public static final String username = "jhitchcock";
	public static final String password = "G8NfoY";
//	public static final String url = "jdbc:mysql://cse.unl.edu/atnguyen";
//	public static final String username = "atnguyen";
//	public static final String password = "E8oL5j";
	static public Connection getConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DataBaseInfo.url, DataBaseInfo.username, DataBaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return conn;
	}

}
