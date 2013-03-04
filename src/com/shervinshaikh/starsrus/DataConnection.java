package com.shervinshaikh.starsrus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataConnection {

	//common elements throughout the entire program
	static Connection conn;
	static String strConn = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	static String strUsername = "cs174a_shervinshaikh";
	static String strPassword = "computer";
	
	public static void main(String[] args) throws SQLException{
		
		// 1. Load the Oracle JDBC driver for this program
		try {
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		}
		catch ( Exception e){
		e.printStackTrace();
		}
		
		// 2. Test functions for each query
		print_all();
		registerCustomer();
	}
	
	public static void print_all() throws SQLException {
		// Connect to the database

		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		
		// Create a Statement
		Statement stmt = conn.createStatement();
		
		// Specify the SQL Query to run
		ResultSet rs = stmt.executeQuery ("SELECT * FROM CS174A.movies");
		
		// Iterate through the result and print the data
		System.out.println("result:");
		
		while(rs.next()) {
			// Get the value from column "columnName" with integer type
			System.out.println(rs.getInt("M_ID"));
			// Get the value from column "columnName" with float type
			System.out.println(rs.getString("M_NAME"));
			// Get the value from the third column with string type
			System.out.println(rs.getString(3));
		}
		
		// don't miss this
		rs.close();
	}
	
	
	public static int testPreStmt(int id, double value) throws SQLException{
		PreparedStatement pre_statement;
		String updateSuppSQL = "update tablename set columnName = ? where id = ?";
		pre_statement = conn.prepareStatement(updateSuppSQL);
		
		// Replace the first ? with value
		pre_statement.setDouble(1, value);
		
		// Replace the second ? with id
		pre_statement.setInt(2, id);
		
		// Execute updates
		int n = pre_statement.executeUpdate();
		System.out.println("updates :" + n );
		pre_statement.close();
		return n;
	}
	
	
	public static int depositMoney(int id, double value) throws SQLException{
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
				
		// Create a Statement
		Statement stmt = conn.createStatement();
				
		// Specify the SQL Query to run
		ResultSet rs = stmt.executeQuery ("SELECT balance FROM Customer C WHERE C.id =" + id);
		
		int balance = rs.getInt("BALANCE");
		rs.close();
		
		balance += value;
		
		PreparedStatement pre_statement;
		String updateSuppSQL = "update tablename set columnName = ? where id = ?";
		pre_statement = conn.prepareStatement(updateSuppSQL);
		
		// Replace the first ? with value
		pre_statement.setDouble(1, balance);
		
		// Replace the second ? with id
		pre_statement.setInt(2, id);
		
		// Execute updates
		int n = pre_statement.executeUpdate();
		System.out.println("updates :" + n );
		pre_statement.close();
		return n;
	}
	
	
	public static boolean registerCustomer() throws SQLException{
		Statement stmt = null;
		//conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		
		      //STEP 2: Register JDBC driver
		      //Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to a selected database...");
		      conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		      System.out.println("Connected database successfully...");
		      
		      //STEP 4: Execute a query
		      System.out.println("Inserting records into the table...");
		      stmt = conn.createStatement();
		      
		      
		      String sql = "INSERT INTO Stock(symbol, currentprice, closeprice, sname, dob, mtitle, srole, syear, contract) " 
		    		  + "VALUES ('STC', 32.50, 32.50, 'Tom Cruise', '03-JUL-62', 'Jerry Maguire', 'Actor', 1996, 5000000)";
		      stmt.executeUpdate(sql);
		      //sql = "INSERT INTO Registration " + "VALUES (101, 'Mahnaz', 'Fatma', 25)";
		      //stmt.executeUpdate(sql);
		      //sql = "INSERT INTO Registration " + "VALUES (102, 'Zaid', 'Khan', 30)";
		      //stmt.executeUpdate(sql);
		      //sql = "INSERT INTO Registration " + "VALUES(103, 'Sumit', 'Mittal', 28)";
		      //stmt.executeUpdate(sql);
		      System.out.println("Inserted records into the table...");
		      conn.close();
		   System.out.println("Goodbye!");
		   
		conn.close();
		return true;
	}

}