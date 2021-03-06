package com.shervinshaikh.starsrus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class CustomerPane extends JPanel {
	int taxid = Log.taxid;
	int buyPrice = 22;
	
    JTabbedPane tabbedPane;
    JTextField amountD, amountW;
    JTextField sharesB,sharesS;
	BalanceTable balanceTable;
    StockTable stockInfoPane;
    JComponent panel7,panel5,panel6,panel4;
    JScrollPane symbolScroll2;
    int first_top_date;
    int second_top_date;
    String[] movieInfo;
    String[] stockSymbols;
    String movieName;
    Object[][] balances;
    Object si[] = new Object[8];
    JList list, list2;
    TransactionHistoryTable newContentPane;

    JLabel movProdYear;
    JLabel movRanking;
    
    JLabel labelUserId;
    JLabel labelMarketBal;
    JLabel labelStockBal;


    public CustomerPane() {
        super(new GridLayout(1, 1));
        
        // Initialize global variables
        System.out.println("taxid: " + taxid);
        try { stockSymbols = DataConnection.getStockSymbols(); } catch (SQLException e1){ System.out.println("ERROR getting Stock Symbols"); }


        // DEPOSIT PANEL
        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(800, 600));
        JButton submitD = new JButton("Submit");
        amountD = new JTextField(20);
        submitD.addActionListener(new DepositListener());
        JPanel p1 = new JPanel();
        p1.add(amountD);
        p1.add(submitD);
        tabbedPane.addTab("Deposit", p1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);



        // WITHDRAW PANEL
        JButton submitW = new JButton("Submit");
        amountW = new JTextField(20);
        submitW.addActionListener(new WithdrawListener());
        JPanel p2 = new JPanel();
        p2.add(amountW);
        p2.add(submitW);
        tabbedPane.addTab("Withdraw", p2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);



        // BUY PANEL
        JPanel p3 = new JPanel();
        p3.setLayout(null);
        sharesB = new JTextField(20);
        JLabel buylabel = new JLabel("# Shares:");
        list = new JList(stockSymbols);
        JScrollPane symbolScroll = new JScrollPane(list);
        symbolScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        symbolScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        list.setVisibleRowCount(4);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JButton purchase = new JButton("Purchase");
        purchase.addActionListener(new BuyListener());
        sharesB.setBounds(105,50, 60, 20);
        buylabel.setBounds(50,50,55,20);
        symbolScroll.setBounds(50,75,350,150);
        purchase.setBounds(410,180,90,30);
        p3.add(buylabel);
        p3.add(sharesB);
        p3.add(symbolScroll);
        p3.add(purchase);
        tabbedPane.addTab("Buy", p3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);


        
        // SELL PANEL
        panel4 = makeTextPanel("Panel #4");
        panel4.setPreferredSize(new Dimension(410, 50));
        panel4.setLayout(null);
        sharesS = new JTextField(20);
        JLabel selllabel = new JLabel("# Shares:");
        String[] userSymbols = {};
        try { 
        	userSymbols = DataConnection.getOwnedSymbols(taxid); 
        } catch (SQLException e) { 
        	System.out.println("ERROR unable to get stock account symbols"); 
        }
        
        list2 = new JList(userSymbols);
        symbolScroll2 = new JScrollPane(list2);
        symbolScroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        symbolScroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        list2.setVisibleRowCount(4);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JButton sells = new JButton("Sell");
        JButton compTrans = new JButton("Complete Transaction");
        sells.addActionListener(new SellListener());
        compTrans.addActionListener(new CompTransaction());
        sharesS.setBounds(105,50, 60, 20);
        selllabel.setBounds(50,50,55,20);
        symbolScroll2.setBounds(50,75,350,150);
        sells.setBounds(410,180,90,30);
        compTrans.setBounds(410,220,180,30);
        panel4.add(selllabel);
        panel4.add(sharesS);
        panel4.add(symbolScroll2);
        panel4.add(sells);
        panel4.add(compTrans);
        tabbedPane.addTab("Sell", panel4);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);



        // SHOW BALANCE
        panel5 = makeTextPanel("Panel #5");
        panel5.setPreferredSize(new Dimension(410, 50));
        panel5.setLayout(null);
        int nAccounts = 2;
        try{ nAccounts = DataConnection.getNAccounts(taxid);
        } catch (SQLException e2){ System.out.println("ERROR getting number of Accounts");}       
        balances = new Object[nAccounts][1];
        try{ balances = DataConnection.getBalances(taxid);
        } catch (SQLException e3){ System.out.println("ERROR getting balances of all accounts");}
		balanceTable = new BalanceTable(taxid);
        balanceTable.setOpaque(true); //content panes must be opaque
        panel5.add(balanceTable);
        balanceTable.setBounds(0,130,300,300);
		JButton refreshBalance = new JButton("Refresh");
		refreshBalance.addActionListener(new RefreshListener());
		refreshBalance.setBounds(20,105,150,20);
		panel5.add(refreshBalance);
        labelUserId = new JLabel("User ID: " + taxid);
        labelMarketBal = new JLabel("Market Balance: " + balances[1][0]);
        labelUserId.setBounds(20,30, 150, 20);
        labelMarketBal.setBounds(20,55,200,20);
        panel5.add(labelUserId);
        panel5.add(labelMarketBal);
        tabbedPane.addTab("Show Balance", panel5);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);


        // Transaction HISTORY
        panel6 = makeTextPanel("Panel #6");
        panel6.setPreferredSize(new Dimension(410, 50));
        panel6.setLayout(null);
        newContentPane = new TransactionHistoryTable(taxid);
        newContentPane.setOpaque(true); //content panes must be opaque
        panel6.add(newContentPane);
        newContentPane.setBounds(0,0,600,350);
        tabbedPane.addTab("Transaction History", panel6);
        tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);


        // STOCK PROFILE
        panel7 = makeTextPanel("Panel #7");
        panel7.setPreferredSize(new Dimension(410, 50));
        panel7.setLayout(null);
        JLabel selectStockLabel = new JLabel("Select stock:");
        try{ si = DataConnection.getStockInfo(stockSymbols[0]); } catch (SQLException e) { System.out.println("ERROR unable to retrieve stock profile");}
        stockInfoPane = new StockTable(si[0].toString(), si[1].toString(), si[2].toString(), si[3].toString(), si[4].toString(), si[5].toString(), si[6].toString(), si[7].toString());
        stockInfoPane.setOpaque(true); //content panes must be opaque
        panel7.add(stockInfoPane);
        stockInfoPane.setBounds(0, 60, 600, 300);
        JComboBox stockInfoList = new JComboBox(stockSymbols);
        stockInfoList.addItemListener(new StockSelect());
        selectStockLabel.setBounds(20,0,80,20);
        stockInfoList.setBounds(20, 20, 80, 20);
        panel7.add(stockInfoList);
        panel7.add(selectStockLabel);
        tabbedPane.addTab("Stock Info", panel7);
        tabbedPane.setMnemonicAt(6, KeyEvent.VK_7);



        // MOVIE PROFILE
        JComponent panel8 = makeTextPanel("Panel #8");
        panel8.setPreferredSize(new Dimension(410, 50));
        panel8.setLayout(null);
        String[] movieOptions = { "Yahoo", "Google", "Apple", "Qualcomm", "Microsoft" };
        try{ movieOptions = DataConnection.getMovies();
        } catch (SQLException e){ System.out.println("ERROR getting movie names");}
        JComboBox moviePicker = new JComboBox(movieOptions);
        moviePicker.addItemListener(new MoviePickListener());
        moviePicker.setBounds(50,0,200,20);
        movieName = movieOptions[0];
        // movieInfo[4] = {id, name, prod year, ranking}
        try{ movieInfo = DataConnection.getMovieInfo(movieOptions[0]); }
                catch (SQLException e){ 
                	System.out.println("Error getting movie info for " + movieOptions[0]);
                	System.out.println(e.getMessage());
                }
        JLabel movTitleLabel = new JLabel("Title:" );
        movTitleLabel.setBounds(0,0,50,20);
        movProdYear = new JLabel("Production year: " + movieInfo[2]);
        movProdYear.setBounds(0,20,250,20);
        movRanking = new JLabel("Ranking: " + movieInfo[3]);
        movRanking.setBounds(0,40,150,20);
        JButton viewReviews = new JButton("Get Reviews");
        viewReviews.addActionListener(new reviewButtonListener());
        viewReviews.setBounds(0, 260, 150, 20);
        JButton viewTopMovies = new JButton("Top Movies");
        viewTopMovies.addActionListener(new topMovButtonListener());
        viewTopMovies.setBounds(0,300,120,20);
        //top movies stuff
        JLabel yearFromLabel = new JLabel("From:");
        yearFromLabel.setBounds(0,280,50,20);
        panel8.add(yearFromLabel);
        JLabel yearToLabel = new JLabel ("To:");
        yearToLabel.setBounds(130,280,50,20);
        panel8.add(yearToLabel);
        // year selection
        int[] years = {1900, 2020};
        try{
        	years = DataConnection.getYears();
        } catch (SQLException e){
        	System.out.println("ERROR");
        }
        first_top_date = years[0];
        second_top_date = years[1];
        JSpinner sp1 = new JSpinner(new SpinnerNumberModel(years[0], years[0],years[1],1));
        JSpinner sp2 = new JSpinner(new SpinnerNumberModel(years[1],years[0],years[1],1));
        sp1.setBounds(50,280,80,20);
        sp2.setBounds(160,280,80,20);
        sp1.addChangeListener(new firstYear());
        sp2.addChangeListener(new secondYear());
        panel8.add(sp1);
        panel8.add(sp2);
        panel8.add(viewTopMovies);
        panel8.add(viewReviews);
        panel8.add(moviePicker);
        panel8.add(movTitleLabel);
        panel8.add(movProdYear);
        panel8.add(movRanking);
        tabbedPane.addTab("Movie Info", panel8);
        tabbedPane.setMnemonicAt(7, KeyEvent.VK_8);
        //Add the tabbed pane to this panel.
        add(tabbedPane);
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    
    // LISTENER CLASSES
    class DepositListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Call a function that accesses the database and adds money to account
            double amount = Double.parseDouble(amountD.getText());

           try {
                DataConnection.depositMoney(taxid, amount);
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.out.println("ERROR unable to deposite money");
            }
            
           JOptionPane.showMessageDialog(null, "Deposit of $" + amount + " Complete!");
           
        }

    }

    class WithdrawListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            double amount = Double.parseDouble(amountW.getText());
            double balance = 0;
           try {
                balance = DataConnection.withdrawMoney(taxid, amount);
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.out.println("ERROR unable to withdraw money");
            }
           if(balance >= 0){
        	   JOptionPane.showMessageDialog(null, "Withdrawal of $" + amount + " Complete!");
           }
           else {
        	   JOptionPane.showMessageDialog(null, "Unable to complete withdrawal, funds too low");
           }
           
        }

    }

    class BuyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	double v = 0;
        	try{ 
        		v = DataConnection.buyStocks(taxid, Integer.parseInt(sharesB.getText()), list.getSelectedValue().toString());
        	} catch (SQLException e) { System.out.println("ERROR unable to buy stocks"); System.out.println(e.getMessage());}
        	if(v == -1){
        		JOptionPane.showMessageDialog(null, "Not enough funds to complete purchase");
        	}
        	else if(v == -2){
        		JOptionPane.showMessageDialog(null, "Market is closed, cannot buy stocks");
        	}
    		else{ 
    			JOptionPane.showMessageDialog(null, "Purchase Done!");
    		}

        	
        	updateSellandHistory();
        }
        
        

    }
    class SellListener implements ActionListener{
    	
    	@Override
    	public void actionPerformed(ActionEvent arg0){
    		//do sell stuff here
    		
    		double v = 0;
        	try{
        		int nshares = Integer.parseInt(sharesS.getText());
        		String symbol = list2.getSelectedValue().toString().substring(0,3);
        		String str = (list2.getSelectedValue().toString().substring(16,25));
        		StringTokenizer st = new StringTokenizer(str, ",");
        		if(st.hasMoreElements()){
        			str = (String) st.nextElement();
        		}
        		double buyPrice = Double.parseDouble(str);
        		System.out.println("symbol: " + symbol + ", buy price: " + buyPrice);
        		
        		System.out.println("#shares selling: " + nshares + " of " + symbol);
        		v = DataConnection.sellStocks(taxid, nshares, symbol, buyPrice);
        	} catch (SQLException e) { System.out.println("ERROR unable to sell stocks"); }
        	
        	if(v == -1){
        		JOptionPane.showMessageDialog(null, "Not enough stocks to complete sell");
        	}
        	else if(v == -2){
        		JOptionPane.showMessageDialog(null, "Market is closed, cannot sell stocks");
        	}
    		else{ 
    			JOptionPane.showMessageDialog(null, "Stocks Sold!");
    		}
    		
    		updateSellandHistory();
    	}
    	
    	
    }
    //TODO
    class CompTransaction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0){
        	DataConnection.completePurchase(taxid);
			
            //do stuff here
        }
    }

    class reviewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0){
            @SuppressWarnings("unused")
			ReviewBoard revBoard = new ReviewBoard(movieName);
            // DO STUFF HERE
            
        }
    }

    class topMovButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0){
            @SuppressWarnings("unused")
			TopMovieBoard topBoard = new TopMovieBoard(first_top_date, second_top_date);
            //do stuff here
        }
    }

    class MoviePickListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event){
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String item = event.getItem().toString();
                try{ movieInfo = DataConnection.getMovieInfo(item); }
                catch (SQLException e){ System.out.println("Error getting movie info for " + item);}
                // movieinfo[4] = {id, name, year, ranking}
                //prod_date_plus=movieInfo[2];
                //ranking_plus=movieInfo[3];
                movProdYear.setText("Production Year: " + movieInfo[2]);
                movRanking.setText("Ranking: " + movieInfo[3]);
                
                movieName = movieInfo[1];
            }
        }
    }
    class firstYear implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e){
            JSpinner spinner = ( JSpinner ) e.getSource();
            SpinnerModel spinnerModel = spinner.getModel();
            //System.out.println(spinnerModel.getValue());
            first_top_date = Integer.parseInt(spinnerModel.getValue().toString());
            //System.out.println(first_top_date);
        }
    }
    class secondYear implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e){
            JSpinner spinner = ( JSpinner ) e.getSource();
            SpinnerModel spinnerModel = spinner.getModel();
            System.out.println(spinnerModel.getValue());
            second_top_date = Integer.parseInt(spinnerModel.getValue().toString());
        }
    }

	class RefreshListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0){


            int nAccounts = 2;
	        try{ nAccounts = DataConnection.getNAccounts(taxid);
	        } catch (SQLException e2){ System.out.println("ERROR getting number of Accounts");}
	        
	        balances = new Object[nAccounts][1];
	        
	        try{ balances = DataConnection.getBalances(taxid);
	        } catch (SQLException e3){ System.out.println("ERROR getting balances of all accounts");}
	        
	        labelUserId.setText("User ID: " + taxid);
	        labelMarketBal.setText("Market Balance: " + balances[1][0]);
	        //labelStockBal.setText("Stock Balance: " + balances[3]);

	        panel5.remove(balanceTable);
	        balanceTable = new BalanceTable(taxid);
	        balanceTable.setOpaque(true); //content panes must be opaque
	        panel5.add(balanceTable);
	        balanceTable.setBounds(0,130,300,300);
		}
	
	}
	
    class StockSelect implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event){
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object item = event.getItem();
                try{ si = DataConnection.getStockInfo(item.toString()); } catch (SQLException e) { System.out.println("ERROR unable to retrieve stock profile");}  
                StockTable blah = new StockTable(si[0].toString(), si[1].toString(), si[2].toString(), si[3].toString(), si[4].toString(), si[5].toString(), si[6].toString(), si[7].toString());
                blah.setOpaque(true); //content panes must be opaque
                panel7.remove(stockInfoPane);
                panel7.add(blah);
                tabbedPane.repaint();
                panel7.repaint();
                blah.repaint();
                blah.setBounds(0, 60, 600, 300);
            }
        }
    }
    
    private void updateSellandHistory(){
    	//update history
    	panel6.remove(newContentPane);
    	newContentPane = new TransactionHistoryTable(taxid);
    	newContentPane.setOpaque(true);

        newContentPane.setBounds(0,0,600,350);
        
    	panel6.add(newContentPane);
    	tabbedPane.revalidate();
    	panel6.revalidate();
    	newContentPane.revalidate();
    	
    	//update sell
    	String[] userSymbols = {};
        try { 
        	userSymbols = DataConnection.getOwnedSymbols(taxid); 
        } catch (SQLException e) { 
        	System.out.println("ERROR unable to get stock account symbols"); 
        }
        
        panel4.remove(symbolScroll2);
        list2 = new JList(userSymbols);
        symbolScroll2 = new JScrollPane(list2);
        symbolScroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        symbolScroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        list2.setVisibleRowCount(4);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        symbolScroll2.setBounds(50,75,350,150);
        panel4.add(symbolScroll2);
        
    }



    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Trader Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Add content to the window.
        frame.add(new CustomerPane(), BorderLayout.CENTER);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
