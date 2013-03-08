package com.shervinshaikh.starsrus;

import java.sql.Connection;
import java.sql.Date;
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
	static String cMonth = "Apr";
	static String nMonth = "May";
	static String name = "Cindy Laugher";
	
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

		//if(buyStocks(1022, 2, "SKB") == -1){ System.out.println("unable to complete purchase"); }
		//else{ System.out.println("purchase complete"); }
		
		//getMovieReviews("Chicago");

		//getMovieInfo("Head of State");

		//getMovies();

		//topMovies(1997, 2005);
		
		//getStockInfo("SKB");
		
		//getBalances(1022);
		
		//openMarket();
		//closeMarket();
		
		//setDate("18-Apr-13");
		//setStockPrice("SKB", 45.0);
		
		//getTransactionHistory(1022);
		
		//sellStocks(1022, 2, "SKB", 30);
		
		//getOwnedSymbols(1022);
		
		//getActiveCustomers();
		
		//genDTER();
		
		//genMonthlyStatement(name);
		
		recordBalances();
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
		conn.close();
		return n;
	}
	
	
	
	// TODO create a MarketAccount & place money into it when registering the customer
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
	
	
	// TODO check to see if person is a manager
	public static int validUser(String username, String pw) throws SQLException{
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		//System.out.println("connection complete");
		ResultSet rs = stmt.executeQuery("SELECT * FROM Customer WHERE username ='" + username + "' AND pw='" + pw + "'");
		//System.out.println("Query run");
		
		if(rs.next()){
			int v = rs.getInt("ismanger");
			if(v == 1){
				//System.out.println("login successful!");
				conn.close();
				return 2;
			}
			else if(v == 0){
				conn.close();
				return 1;
			}
		}
		
		rs.close();
		conn.close();
		return 0;
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
		
		pstmt.setDouble(1, amount);
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
		int marketID=0, stockID = 0, oldshares = -1, pshares = nshares;
		String date = "";
		
		if(!isMarketOpen()){
			System.out.println("Market is closed, cannot buy or sell");
			conn.close();
			return -2;
		}
		
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
		value += stockPrice*(Double.parseDouble("" + nshares));
		double balance = withdrawMoney(taxid, value);
		if(balance == -1){
			conn.close();
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
		if(oldshares >= 0){ 
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
	
	public static double sellStocks(int taxid, int nshares, String symbol, double buyPrice) throws SQLException {
		int marketID = 0, stockID = 0, currentShares = 0, newShares = 0;
		double currentPrice = 0, earnings = 0;
		String date = getTodaysDate();
		
		if(!isMarketOpen()){
			System.out.println("Market is closed, cannot buy or sell");
			conn.close();
			return -2;
		}
		
		currentPrice = Double.parseDouble(getStockInfo(symbol)[0].toString());
		earnings = (currentPrice - buyPrice)*nshares;
		System.out.println("earnings:  " + earnings);
		
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM StockAccounts WHERE taxid= " + taxid + " AND symbol = '" + symbol + "'");
		if(rs.next()){
			currentShares = rs.getInt("nshares");
			stockID = rs.getInt("stockid");
		}
		
		// if not enough shares in account, end the sell process
		System.out.println("current shares:" + currentShares + ", selling shares: " + nshares);
		newShares = currentShares-nshares;
		if(newShares < 0){
			System.out.println("Cannot complete sell");
			conn.close();
			return -1;
		}
		try{
		String sql = "UPDATE StockAccounts SET nshares = ? WHERE taxid = ? AND symbol = ?";
		PreparedStatement p = conn.prepareStatement(sql);
		p.setInt(1, newShares);
		p.setInt(2,  taxid);
		p.setString(3,  symbol);
		System.out.println("getting ready to execute update");
		p.executeUpdate();
		System.out.println("table updated");
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		// get the Market ID
		rs = s.executeQuery("SELECT marketid FROM MarketAccounts WHERE taxid =" + taxid);
		if(rs.next()){
			marketID = rs.getInt(1);
		}
		
		// DEPOSIT money into Market Account
		depositMoney(taxid, earnings-20);
		
		
		// RECORD Transaction
		recordTransaction(marketID, stockID, taxid, "sell", symbol, newShares, currentPrice, date, 0.0);
		conn.close();
		return earnings; 
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
	
	public static int getNumTrans2(String name) throws SQLException {
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		int taxid = 0;
		ResultSet rs = s.executeQuery("SELECT taxid FROM Customer WHERE cname='" + name + "'");
		if(rs.next()){
			taxid = rs.getInt(1);
		}
		int nTrans = getNumTrans(taxid);
		
		return nTrans;
	}

	public static int getNumTrans(int taxid) throws SQLException {
		int nTrans = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Transactions WHERE taxid=" + taxid);
		if(rs.next()){
			nTrans = rs.getInt(1);
		}
		conn.close();
		System.out.println("number of transactions for taxid: " + taxid + " is " + nTrans);
		return nTrans;
	}
	
	public static String[][] getTransactionHistory(int taxid) throws SQLException {
		int nTrans = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		
		nTrans = getNumTrans(taxid);
		String[][] trans = new String[nTrans][7];

		ResultSet rs = s.executeQuery("SELECT * FROM Transactions WHERE taxid=" + taxid);
		for(int i=0; rs.next(); i++){
			trans[i][0]= "" + rs.getInt("stockID");
			trans[i][1]= rs.getString("ttype");
			trans[i][2]= rs.getString("symbol");
			trans[i][3]= "" + rs.getInt("nshares");
			trans[i][4]= "" + rs.getDouble("price");

			// This could possible turn into an error:
			trans[i][5]= rs.getString("tdate").substring(0,10);
			trans[i][6]= "" + rs.getDouble("earnings");
			System.out.println("Trans History for TaxID: " + taxid);
			System.out.println("stockID: " + trans[i][0] + ", transType: " + trans[i][1] + ", symbol: " + trans[i][2] + ", numShares: " + trans[i][3] + ", price: " + trans[i][4]);
		}
		conn.close();
		return trans;
	}
	
	public static String getTodaysDate() throws SQLException{
		String date = "";
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT cDate FROM Operations");
		if(rs.next()){
			date = rs.getString(1);
		}
		conn.close();
		return "to_date('" + date + "', 'yyyy/mm/dd hh24:mi:ss')";
	}
	
	public static Date getTodaysDate2() throws SQLException{
		Date date = null;
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT cDate FROM Operations");
		if(rs.next()){
			date = rs.getDate(1);
		}
		System.out.println(date);
		conn.close();
		return date;
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
	
	
	// TODO double check
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
		rs.close();
		s.close();
		conn.close();
		return symbols;
	}
	
	public static String[] getOwnedSymbols(int taxid) throws SQLException {
		int nStocks = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM StockAccounts WHERE taxid=" + taxid);
		if(rs.next()){
			nStocks = rs.getInt(1);
			System.out.println("Number of stock accounts: " + nStocks);
		}
		
		String[] symbols = new String[nStocks];
		rs = s.executeQuery("SELECT symbol FROM StockAccounts WHERE taxid=" + taxid);
		for(int i=0; rs.next(); i++){
			symbols[i] = rs.getString(1);
		}
		rs.close();
		s.close();
		conn.close();
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

		conn.close();
		return movies;
	}


	public static String[][] getMovieReviews(String movieName) throws SQLException {
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

		conn.close();
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

		conn.close();
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

		conn.close();
		return years;
	}

	public static String[][] topMovies(int beg, int end) throws SQLException{
		int nMovies = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		// get number of movies in that range
		ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM CS174A.movies WHERE m_year >="+ beg +" AND m_year <=" + end + " AND m_ranking=5.0");
		if(rs.next()){
			nMovies = rs.getInt(1);
			System.out.println("Number of movies within " + beg + " to " + end + ": " + nMovies);
		}

		// place movies in they array
		String[][] movies = new String[nMovies][1];
		rs = s.executeQuery("SELECT m_name FROM CS174A.movies WHERE m_year >="+ beg +" AND m_year <=" + end + " AND m_ranking=5.0");
		for(int i=0; rs.next(); i++){
			movies[i][0] = rs.getString(1);
			System.out.println("top movie: " + movies[i][0]);
		}

		conn.close();
		return movies;
	}

	public static Object[][] getBalances2(String name) throws SQLException {
		int taxid = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT taxid FROM Customer WHERE cname='" + name + "'");
		if(rs.next()){
			taxid = rs.getInt(1);
		}
		rs.close();
		conn.close();
		return getBalances(taxid);
	}
	
	public static Object[][] getBalances(int taxid) throws SQLException{
		int nAccounts = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		try{ nAccounts = getNAccounts(taxid);} catch (SQLException e){ System.out.println("ERROR getting number of accounts");}
		
		Object[][] b = new Object[nAccounts][1];
		// places market account ID & Balance into double array
		ResultSet rs = s.executeQuery("SELECT marketID, balance FROM MarketAccounts WHERE taxid =" + taxid);
		if(rs.next()){
			b[0][0] = 0.0 + rs.getInt(1);
			b[1][0] = rs.getDouble(2);
			System.out.println("Market Account ID: " + b[0][0] + "; Balance $" + b[1][0]);
		}
		// place stock accounts IDs & Balances into doubles array
		rs = s.executeQuery("SELECT * FROM StockAccounts WHERE taxID =" + taxid);
		for(int i=2; rs.next(); i+=3){
			b[i][0] = rs.getInt(1);
			b[i+1][0] = rs.getInt(3);
			b[i+2][0] = rs.getString(4);
			System.out.println("Stock Account ID: " + b[i][0] + ",  # Shares: " + b[i+1][0] + ", Symbol: " + b[i+2][0]);
		}
		conn.close();
		return b;
	}

	public static int getNAccounts2(String name) throws SQLException{
		int taxid = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT taxid FROM Customer WHERE cname='" + name + "'");
		if(rs.next()){
			taxid = rs.getInt(1);
		}
		rs.close();
		conn.close();
		return getNAccounts(taxid);
	}
	
	public static int getNAccounts(int taxid) throws SQLException{
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
		conn.close();
		return nAccounts;
	}
	
	public static Object[] getStockInfo(String symbol) throws SQLException {
		//data = {0currentPrice, 1closePrice, 2name, 3DoB, 4movie, 5role, 6year, 7contract}
		Object[] data = new Object[8];
		
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		
		ResultSet rs = s.executeQuery("SELECT * FROM Stock WHERE symbol='" + symbol +"'");
		if(rs.next()){
			data[0] = rs.getDouble("currentPrice");
			data[1] = rs.getDouble("closePrice");
			data[2] = rs.getString("sname");
			data[3] = rs.getString("DOB");
			data[4] = rs.getString("mtitle");
			data[5] = rs.getString("srole");
			data[6] = "" + rs.getInt("syear");
			data[7] = "" + rs.getDouble("contract");
			System.out.println("Stock Profile for: " + symbol);
			System.out.println("curPrice: " +data[0]+", closePrice: " +data[1]+ ", name: " +data[2]+ ", DOB: " +data[3]+ ", Movie Title: " +data[4]+ ", Role: " +data[5]+ ", Year: " +data[6]+ ", Contract: " + data[7]);
		}
		conn.close();
		return data;
	}


	

	// TEST, DEBUG, DEMO OPERATIONS
	
	// TODO test all of the methods below, ex: open Market just sits at executeUpdate
	public static void openMarket() throws SQLException {
		System.out.println("About to connect");
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		System.out.println("connected and now creating statement");
		Statement s = conn.createStatement();
		System.out.println("about to execute update");
		s.executeUpdate("UPDATE Operations SET isOpen = 1");
		System.out.println("Market Opened!");
		s.close();
		conn.close();
	}

	public static void closeMarket() throws SQLException {
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		PreparedStatement p = conn.prepareStatement("UPDATE operations SET isOpen = 0");
		p.executeUpdate();
		System.out.println("Market Closed!");
		conn.close();
	}
	
	public static void setDate(String date) throws SQLException {
		//recordBalances();
		
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		PreparedStatement p = conn.prepareStatement("UPDATE operations SET cDate = ?");
		p.setString(1, date);
		p.executeUpdate();
		System.out.println("Date updated to " + date);
		conn.close();
	}

	public static void setStockPrice(String symbol, Double price) throws SQLException {
		conn = DriverManager.getConnection(strConn,strUsername,strPassword);
		PreparedStatement p = conn.prepareStatement("UPDATE Stock SET currentPrice = ? WHERE Symbol = ?");
		p.setDouble(1, price);
		p.setString(2, symbol);
		System.out.println("about to execute update");
		p.executeUpdate();
		System.out.println("New " + symbol + ": $" + price);
		conn.close();
	}


	public static boolean isMarketOpen() throws SQLException {
		boolean isOpen = true;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT isOpen FROM Operations");
		if(rs.next()){
			if(rs.getInt(1) == 1){
				conn.close();
				return true;
			}
			else {
				conn.close();
				return false;
			}
		}
		s.close();
		rs.close();
		conn.close();
		return isOpen;
	}
	
	
	// MANAGER INTERFACE
	//
	//
	public static String[] getCustomers() throws SQLException {
		int nCustomers = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		// get number of market accounts
		ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Customer WHERE ismanager = 0");
		if(rs.next()){
			nCustomers = (rs.getInt(1));
			System.out.println("# of Customer Accounts: " + nCustomers);
		}
		
		String[] customers = new String[nCustomers];
		rs = s.executeQuery("SELECT cname FROM Customer WHERE ismanager = 0");
		for(int i=0; rs.next(); i++){
			customers[i] = rs.getString(1);
		}

		rs.close();
		conn.close();
		return customers;
	}
	
	// list of customers who have bought or sold at least 1,000 shares in the current month
	public static String[] getActiveCustomers() throws SQLException {
		int numActiveCustomers = 0;
		// get number of active customers in current month
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();

		// get number of market accounts
		ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM Customer WHERE ismanager = 0");
		if(rs.next()){
			numActiveCustomers = (rs.getInt(1));
			System.out.println("# of Customer Accounts: " + numActiveCustomers);
		}
		// place active customers (cname, taxid) into string array
		String[] activeCustomers = new String[numActiveCustomers];
		rs = s.executeQuery("SELECT cname FROM Customer WHERE ismanager = 0");
		for(int i=0; rs.next(); i++){
			activeCustomers[i] = rs.getString(1);
		}
		rs.close();
		conn.close();
		return activeCustomers;
	}
	
	// list customers (name, state, earnings) who earned more than $10,000 in the last month
	public static Object[][] genDTER() throws SQLException {
		int n = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM (SELECT taxid, SUM(earnings) AS totalEarnings FROM Transactions WHERE tdate BETWEEN to_date('" + cMonth + "', 'MON') AND to_date('"+ nMonth + "', 'MON') GROUP BY taxid)");
		if(rs.next()){
			n = rs.getInt(1);
			System.out.println("# Customers where earnings>$10,000: " + n);
		}
		Object[][] customers = new String[n][4];
		// get the taxid for each customer
		int taxid[] = new int[n];
		rs = s.executeQuery("SELECT * FROM (SELECT taxid, SUM(earnings) AS totalEarnings FROM Transactions WHERE tdate BETWEEN to_date('" + cMonth + "', 'MON') AND to_date('"+ nMonth + "', 'MON') GROUP BY taxid)");
		for(int i=0; rs.next(); i++){
			taxid[i] = rs.getInt("taxid");
			customers[i][2] = "" + rs.getDouble("totalearnings");
			System.out.println("taxid: " + taxid[i] + ", earnings: " + customers[i][2]);
		}
		
		for(int j=0; j<n; j++){
			// get the names and states of customer
			rs = s.executeQuery("SELECT cname, state FROM Customer WHERE taxid=" + taxid[j]);
			if(rs.next()){
				customers[j][0] = rs.getString("cname");
				customers[j][1] = rs.getString("state");
				System.out.println(customers[j][0] + " " + customers[j][1]);
			}
			// get interest earned over time period
			rs = s.executeQuery("SELECT interest FROM MarketAccounts WHERE taxid=" + taxid[j]);
			if(rs.next()){
				customers[j][2] = "" + (rs.getDouble(1) + Double.parseDouble(customers[j][2].toString()));
				System.out.println("total earnings including interest: " + customers[j][2]);
			}
		}
		conn.close();
		return customers;
	}
	
	// TODO generate monthly statement
	public static String[][] genMonthlyStatement(String name) throws SQLException {
		String email = "";
		int taxid = 0;
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		// get taxid and Email of user
		ResultSet rs = s.executeQuery("SELECT taxid, email FROM Customer WHERE cname='" + name + "'");
		if(rs.next()){
			taxid = rs.getInt("taxID");
			email = rs.getString("email");
			System.out.println("name: " + name + ", email: " + email);
		}
		
		String[][] transactions = getTransactionHistory(taxid);
		int nTrans = getNumTrans(taxid);
		String[][] ms = new String[nTrans+2][7];
		System.out.println("name of person: " + name + " email: " + email);
		ms[0][0] = name;
		ms[1][0] = email;
		System.out.println("name of person: " + name);
		
		// place transactions into monthly statement
		for(int i=0; i<nTrans; i++){
			ms[i+2][0] = transactions[i][0];
			ms[i+2][1] = transactions[i][1];
			ms[i+2][2] = transactions[i][2];
			ms[i+2][3] = transactions[i][3];
			ms[i+2][4] = transactions[i][4];
			ms[i+2][5] = transactions[i][5];
			ms[i+2][6] = transactions[i][6];
		}
		
		rs.close();
		conn.close();
		return ms;
	}
	
	// empty the Transactions table
	public static void deleteTransactions() throws SQLException {
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		s.executeQuery("DELETE FROM Transactions");
		System.out.println("all transactions deleted");
		conn.close();
	}
	
	public static void addInterest() throws SQLException{
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("DELETE FROM Transactions");
		
		
		System.out.println("Interest added to all accounts");
		conn.close();
	}
	
	public static void recordBalances() throws SQLException {
		// get todays date
		String date = getTodaysDate();
		System.out.println(date);
		
		conn = DriverManager.getConnection(strConn, strUsername, strPassword);
		Statement s = conn.createStatement();
		
		ResultSet rs = s.executeQuery("SELECT MAX(bdate) FROM Balances");
		if(rs.next()){
			if(rs.getString(1) == date){
				System.out.println("already in database");
				conn.close();
				return;
			}
		}
		
		// get number of customers
		int nCustomers = 0;
		rs = s.executeQuery("SELECT COUNT(*) FROM Customer WHERE ismanager = 0");
		if(rs.next()){
			nCustomers = (rs.getInt(1));
			System.out.println("# of Customer Accounts: " + nCustomers);
		}
		System.out.println("about to store taxid");
		// store taxid
		String[][] customers = new String[nCustomers][2];
		rs = s.executeQuery("SELECT taxid FROM Customer WHERE ismanager = 0");
		for(int i=0; rs.next(); i++){
			customers[i][0] = rs.getString(1);
		}
		System.out.println("about to store balance");
		// store balance
		for(int i=0; i<nCustomers; i++){
			rs = s.executeQuery("SELECT balance FROM MarketAccounts WHERE taxid=" + customers[i][0]);
			if(rs.next()){
				customers[i][1] = rs.getString(1);
			}
		}
		System.out.println("getting ready to insert");
		// insert values into the database
		PreparedStatement p;
		String updateSuppSQL = "INSERT INTO Balances(taxid, balance, bdate) Values(?, ?, "+ date + ")";
		p = conn.prepareStatement(updateSuppSQL);
		
		for(int i=0; i<nCustomers; i++){
			p.setInt(1, Integer.parseInt(customers[i][0]));
			p.setDouble(2,  Double.parseDouble(customers[i][1]));
		}

		System.out.println("All Balances recorded for today");
		conn.close();
	}
}
