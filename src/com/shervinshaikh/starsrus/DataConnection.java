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
	}
	
	public static void print_all() throws SQLException {
		// Connect to the database
		String strConn = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
		String strUsername = "cs174a_shervinshaikh";
		String strPassword = "computer";
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
		// Connect to the database
		String strConn = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
		String strUsername = "cs174a_shervinshaikh";
		String strPassword = "computer";
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

}