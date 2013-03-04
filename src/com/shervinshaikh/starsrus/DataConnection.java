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
		//print_all();
		//registerCustomer(2034, "606-70-7900", "8056930011", "Cindy Laugher", "cindy@hotmail.com", "cindy", "la", "7000 Hollister SB", "CA");
		depositMoney(1022, 500);
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
	
	
	
	
	public static boolean registerCustomer(int taxid, String ssn, String phone, String cname, String email,
				String username, String pw, String address, String state) throws SQLException{
		int ismanager = 0;
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		      
		String sql = "INSERT INTO Customer(taxID, ssn, phone, cname, email, username, pw, address, state, ismanager)" +
				"VALUES(" + taxid + ", '" + ssn + "', '" + phone + "', '" + cname + "', '" + email + "', '" + username +"', '" + pw + "', '" + address + "', '" + state + "', " + ismanager + ")";
		stmt.executeUpdate(sql);
		
			//String sql = "INSERT INTO Stock(symbol, currentprice, closeprice, sname, dob, mtitle, srole, syear, contract) " 
		    //		  + "VALUES ('STC', 32.50, 32.50, 'Tom Cruise', '03-JUL-62', 'Jerry Maguire', 'Actor', 1996, 5000000)";

		      //sql = "INSERT INTO Registration " + "VALUES (101, 'Mahnaz', 'Fatma', 25)";
		      //stmt.executeUpdate(sql);
		   
		conn.close();
		return true;
	}
	
	public static boolean validCustomer(String username, String pw) throws SQLException{
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Customer WHERE username =" + username + " AND pw=" + pw);
		
		if(rs.next()){
			if(rs.getInt(1) == 1) return true;
		}
		
		rs.close();
		conn.close();
		return false;
	}

	public static double depositMoney(int taxid, double value) throws SQLException{
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		
		double amount = value;
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM MarketAccounts WHERE taxID=" + taxid);
		if(rs.next()){
			System.out.println(rs.getInt("balance"));
			amount += rs.getInt("balance");
		}
		rs.close();
		
		PreparedStatement pre_statement;
		String updateSuppSQL = "UPDATE MarketAccounts SET balance = ? WHERE taxID = ?";
		pre_statement = conn.prepareStatement(updateSuppSQL);
		
		pre_statement.setDouble(1, amount);
		pre_statement.setInt(2, taxid);
		
		// Execute updates
		pre_statement.executeUpdate();

		pre_statement.close();
		conn.close();
		
		return amount;
	}
	
}