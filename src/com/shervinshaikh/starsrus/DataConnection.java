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
		
		// TO-DO also create a market account for the person
		//registerCustomer(2034, "606-70-7900", "8056930011", "Cindy Laugher", "cindy@hotmail.com", "cindy", "la", "7000 Hollister SB", "CA");
		
		//depositMoney(1022, 100);
		
		// TO-DO returns a boolean value
		// validUser("billy", "cl");
		
		// if == -1 then unable to withdraw money
		//withdrawMoney(1022, 100);

		//if(buyStocks(1022, 30, "STC") == -1){ System.out.println("unable to complete purchase"); }
		//else{ System.out.println("purchase complete"); }
		
		//getMovieReviews(3);

		//getMovieInfo("Head of State");

		//getMovies();

		//topMovies(1997, 2005);
		
		//getBalances(1022);
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
		PreparedStatement pstmt;
		String updateSuppSQL = "update tablename set columnName = ? where id = ?";
		pstmt = conn.prepareStatement(updateSuppSQL);
		
		// Replace the first ? with value
		pstmt.setDouble(1, value);
		
		// Replace the second ? with id
		pstmt.setInt(2, id);
		
		// Execute updates
		int n = pstmt.executeUpdate();
		System.out.println("updates :" + n );
		pstmt.close();
		return n;
	}
	
	
	
	// TO-DO create a MarketAccount & place money into it when registering the customer
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
	
	
	// TO-DO check to see if person is a manager
	public static boolean validUser(String username, String pw) throws SQLException{
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		//System.out.println("connection complete");
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Customer WHERE username ='" + username + "' AND pw='" + pw + "'");
		//System.out.println("Query run");
		
		if(rs.next()){
			if(rs.getInt(1) == 1){
				//System.out.println("login successful!");
				return true;
			}
		}
		
		rs.close();
		conn.close();
		return false;
	}

	public static double depositMoney(int taxid, double value) throws SQLException{
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		
		double amount = value;
		
		ResultSet rs = stmt.executeQuery("SELECT balance FROM MarketAccounts WHERE taxID=" + taxid);
		if(rs.next()){
			double dep = rs.getDouble(1);
			System.out.println("current balance before deposit: " + dep);
			amount += dep;
		}
		rs.close();
		
		System.out.println("new balance after deposit: " + amount);
		
		PreparedStatement pstmt;
		String updateSuppSQL = "UPDATE MarketAccounts SET balance = ? WHERE taxID = ?";
		pstmt = conn.prepareStatement(updateSuppSQL);
		
		pstmt.setDouble(1, amount);
		pstmt.setInt(2, taxid);
		
		// Execute updates
		System.out.println("about to execute");
		pstmt.executeUpdate();
		System.out.println("update complete");

		pstmt.close();
		conn.close();
		
		return amount;
	}
	

	
	// TO-DO fix!!!
	// if cannot be done then return -1
	public static double withdrawMoney(int taxid, double value) throws SQLException{
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		
		double amount = 0;
		
		ResultSet rs = stmt.executeQuery("SELECT balance FROM MarketAccounts WHERE taxID=" + taxid);
		if(rs.next()){
			amount = rs.getInt(1);
			System.out.println("current balance is: "+ amount + " value is at: " + value);
			amount -= value;
			System.out.println("new balance is: " + amount);
		}
		
		if(amount < 0){
			System.out.println("error! can't be done!");
			conn.close();
			return -1;
		}

		PreparedStatement pstmt;
		String updateSuppSQL = "UPDATE MarketAccounts SET balance = ? WHERE taxID = ?";
		pstmt = conn.prepareStatement(updateSuppSQL);
		
		pstmt.setDouble(1, 100);
		pstmt.setInt(2, taxid);
		// Execute updates
		System.out.println("about to execute update");
		pstmt.executeUpdate();
		System.out.println("Account now at:" + amount);

		rs.close();
		pstmt.close();
		conn.close();
		
		return amount;
	}
	
	
	public static double buyStocks(int taxid, int nshares, String symbol) throws SQLException{
		double value = 20;
		double stockPrice = 0;
		int marketID=0, stockID = 0, oldshares = 0, pshares = nshares;
		String date = "";
		
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		
		// WITHDRAW money from Market Account if possible else return -1
		//
		ResultSet rs = stmt.executeQuery("SELECT * FROM Stock WHERE symbol='" + symbol + "' ");
		if(rs.next()){
			stockPrice = rs.getInt("currentprice");
			System.out.println("stock Price = " + stockPrice);
		}
		
		System.out.println("withdrawing money from MarketAccount");
		value += stockPrice*nshares;
		double balance = withdrawMoney(taxid, value);
		if(balance == -1){
			return balance;
		}
		System.out.println("withdrawMoney complete!");
		
		// get the marketID
		rs = stmt.executeQuery("SELECT marketid FROM MarketAccounts WHERE taxid =" + taxid);
		if(rs.next()){
			marketID = rs.getInt(1);
			System.out.println("market id = " + marketID);
		}

		// DEPOSIT shares into Stock Account
		//
		// get stockID & current nshares and add it to the nshares that they're buying
		rs = stmt.executeQuery("SELECT * FROM StockAccounts WHERE taxid =" + taxid + "AND symbol = '" + symbol + "'");
		if(rs.next()){
			oldshares = rs.getInt(3);
			nshares += oldshares;
			System.out.println("number of shares now in the stock account:" + nshares);
			stockID = rs.getInt(1);
			System.out.println("stockID = " + stockID);
		}
		
		// Add Commission to MarketAccount
		addCommission(taxid);
		
		// UPDATE StockAccounts with new nshares values
		PreparedStatement pstmt;
		String updateSuppSQL;
		if(oldshares > 0){ 
			updateSuppSQL = "UPDATE StockAccounts SET nshares = ? WHERE taxID = ? AND symbol = ?";
			System.out.println("updating current StockAccount");
		}
		else {
			int newStockID = 1;
			rs = stmt.executeQuery("SELECT MAX(stockID) FROM StockAccounts");
			if(rs.next()){
				newStockID = rs.getInt(1) + 1;
				System.out.println("new stock id = " + newStockID);
			}
			updateSuppSQL = "INSERT INTO StockAccounts (nshares, taxID, symbol, stockID) VALUES (?, ?, ?, " + newStockID + ")";
			System.out.println("Creating new stock account");
			stockID = newStockID;
		}
		// up
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		pstmt = conn.prepareStatement(updateSuppSQL);
		// set values in statement
		pstmt.setInt(1, nshares);
		pstmt.setInt(2, taxid);
		pstmt.setString(3, symbol);
		// Execute update
		pstmt.executeUpdate();

		// get current date
		date = getTodaysDate();
		
		rs.close();
		stmt.close();
		pstmt.close();
		conn.close();
		
		recordTransaction(marketID, stockID, taxid, "buy", symbol, pshares, stockPrice, date, 0.0);
		return balance;
	}
	
	
	public static void recordTransaction(int marketid, int stockid, int taxid, String ttype, String symbol, int pshares, double price, String date, double earnings) throws SQLException {
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		
		// RECORD transaction in table
		String insertTransaction = "INSERT INTO Transactions(marketID, stockID, taxID, ttype, symbol, nshares, price , tdate, earnings)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, " + date + ", ?)";
		PreparedStatement pstmt2 = conn.prepareStatement(insertTransaction);
		
		pstmt2.setInt(1, marketid);
		pstmt2.setInt(2, stockid);
		pstmt2.setInt(3, taxid);
		pstmt2.setString(4, ttype);
		pstmt2.setString(5, symbol);
		pstmt2.setInt(6, pshares);
		pstmt2.setDouble(7, price);
		pstmt2.setDouble(8, 0);
		pstmt2.executeUpdate();

		pstmt2.close();
		conn.close();
	}
	
	public static String getTodaysDate() throws SQLException{
		String date = "";
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT cDate FROM Operations");
		if(rs.next()){
			date = rs.getString(1);
		}
		
		return "to_date('" + date + "', 'yyyy/mm/dd hh24:mi:ss')";
	}
	
	public static void addCommission(int taxid) throws SQLException{
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);

		// Add Commission of $20 to MarketAccount
		int commission = 20;

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT Commission FROM MarketAccounts WHERE taxid=" + taxid);
		if(rs.next()){
			commission += rs.getInt(1);
		}
		rs.close();
		stmt.close();
		
		PreparedStatement pstmt = conn.prepareStatement("UPDATE MarketAccounts SET Commission=? WHERE taxid=?");
		pstmt.setInt(1, commission);
		pstmt.setInt(2, taxid);
		pstmt.executeUpdate();
		System.out.println("Commission updated to:" + commission);
		pstmt.close();
		conn.close();
	}
	
	
	// TO-DO double check
	public static String[] getStockSymbols() throws SQLException{
		System.out.println("running getStockSymbols");
		int nStocks = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		
		// get number of stocks in database
		ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Stock");
		if(rs.next()){
			nStocks = rs.getInt(1);
			System.out.println("Number of stock in the database: " + nStocks);
		}
		
		// place each stock symbol into the array
		String[] symbols = new String[nStocks];
		rs = s.executeQuery("SELECT symbol FROM Stock");
		for(int i=0; rs.next(); i++){
			symbols[i] = rs.getString(1);
		}
		
		return symbols;
	}

	public static String[] getMovies() throws SQLException{
		int nMovies = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		// get number of movies in database
		ResultSet rs = s.executeQuery("SELECT Count(*) FROM CS174A.movies");
		if(rs.next()){
			nMovies = rs.getInt(1);
			System.out.println("Number of movies in database: " + nMovies);
		}

		// place each movie into string array
		String movies[] = new String[nMovies];
		rs = s.executeQuery("SELECT m_name FROM CS174A.movies");
		for(int i=0; rs.next(); i++){
			movies[i] = rs.getString(1);
			System.out.println("Movie name: " + movies[i]);
		}

		return movies;
	}


	public static String[][] getMovieReviews(int movieName) throws SQLException {
		int nReviews = 0, movieid = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		ResultSet rs = s.executeQuery("SELECT m_id FROM CS174A.movies WHERE m_name = '" + movieName +"'");
		if(rs.next()){
			movieid = rs.getInt(1);
		}



		rs = s.executeQuery("SELECT Count(*) FROM CS174A.reviews WHERE r_mid =" + movieid);
		if(rs.next()){
			nReviews = rs.getInt(1);
			System.out.println("Number of reviews for this movie: " + nReviews);
		}

		String[][] movieReviews = new String[nReviews][2];
		rs = s.executeQuery("SELECT * FROM CS174A.reviews WHERE r_mid=" + movieid);
		for(int i=0; rs.next(); i++){
			movieReviews[i][0] = rs.getString(2);
			System.out.print("author: " + rs.getString(2));
			movieReviews[i][1] = rs.getString(3);
			System.out.println(" review: " + rs.getString(3));
		}

		return movieReviews;
	}
	
	// returns the movie profile info - movieInfo[4] = {id, name, year, ranking}
	public static String[] getMovieInfo(String movieName) throws SQLException {
		String[] movieInfo = new String[4];

		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		ResultSet rs = s.executeQuery("SELECT * FROM CS174A.movies WHERE m_name = '" + movieName + "'");
		if(rs.next()){
			movieInfo[0]= "" + rs.getInt(1);
			movieInfo[1]= rs.getString(2);
			movieInfo[2]= "" + rs.getInt(3);
			movieInfo[3]= "" + rs.getDouble(4);
			System.out.println("id:" + movieInfo[0]);
			System.out.println("name:" + movieInfo[1]);
			System.out.println("production year:" + movieInfo[2]);
			System.out.println("ranking:" + movieInfo[3]);
		}


		return movieInfo;
	}
	
	public static int[] getYears() throws SQLException {
		int[] years = new int[2];

		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		ResultSet rs = s.executeQuery("SELECT MIN(m_year) FROM CS174A.movies");
		if(rs.next()){
			years[0] = rs.getInt(1);
		}
		rs = s.executeQuery("SELECT MAX(m_year) FROM CS174A.movies");
		if(rs.next()){
			years[1] = rs.getInt(1);
		}

		return years;
	}

	public static String[][] topMovies(int beg, int end) throws SQLException{
		int nMovies = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		// get number of movies in that range
		ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM CS174A.movies WHERE m_year >="+ beg +" AND m_year <=" + end);
		if(rs.next()){
			nMovies = rs.getInt(1);
			System.out.println("Number of movies within that range: " + nMovies);
		}

		// place movies in they array
		String[][] movies = new String[nMovies][0];
		rs = s.executeQuery("SELECT m_name FROM CS174A.movies WHERE m_year >="+ beg +" AND m_year <=" + end + " AND m_ranking=5.0");
		for(int i=0; rs.next(); i++){
			movies[i][0] = rs.getString(1);
			System.out.println(movies[i][0]);
		}

		return movies;
	}

	public static Object[] getBalances(int taxid) throws SQLException{
		int nAccounts = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		// get number of market accounts
		ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM MarketAccounts WHERE taxid =" + taxid);
		if(rs.next()){
			nAccounts += (rs.getInt(1) * 2);
			System.out.println("# of Market Accounts: " + nAccounts);
		}

		// get number of stock accounts
		rs = s.executeQuery("SELECT COUNT(*) FROM StockAccounts WHERE taxid =" + taxid);
		if(rs.next()){
			nAccounts += (rs.getInt(1) * 3);
			System.out.println("# of Stock & Market Acounts: " + nAccounts);
		}

		
		Object[] b = new Object[nAccounts];
		// places market account ID & Balance into double array
		rs = s.executeQuery("SELECT marketID, balance FROM MarketAccounts WHERE taxid =" + taxid);
		if(rs.next()){
			b[0] = 0.0 + rs.getInt(1);
			b[1] = rs.getDouble(2);
			System.out.println("Market Account ID: " + b[0] + "; Balance $" + b[1]);
		}
		// place stock accounts IDs & Balances into doubles array
		rs = s.executeQuery("SELECT * FROM StockAccounts WHERE taxID =" + taxid);
		for(int i=2; rs.next(); i+=3){
			b[i] = rs.getInt(1);
			b[i+1] = rs.getInt(3);
			b[i+2] = rs.getString(4);
			System.out.println("Stock Account ID: " + b[i] + ",  # Shares: " + b[i+1] + ", Symbol: " + b[i+2]);
		}


		return b;
	}
	
}