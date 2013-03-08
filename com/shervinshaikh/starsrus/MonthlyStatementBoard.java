/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/6/13
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
 
 package com.shervinshaikh.starsrus;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/6/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */

public class MonthlyStatementBoard extends JFrame
{

    public MonthlyStatementBoard(String targetName)
    {

    	JLabel nameLabel = new JLabel("Name: " );
        JLabel emailLabel = new JLabel("Email: ");
        JLabel iBalLabel = new JLabel("Initial Balance: ");
        JLabel fBalLabel = new JLabel("Final Balance: ");
        JLabel totEarningsLabel = new JLabel("Total Earnings: ");
        JLabel commLabel = new JLabel("Commission: ");
        
        add(nameLabel);
        add(emailLabel);
        add(iBalLabel);
        add(fBalLabel);
        add(totEarningsLabel);
        add(commLabel);
        
        setLayout(new GridLayout(7,1));

        setTitle( "Monthly Statement" );
        setSize( 800,600 );
        setResizable( true );

        String[] columnNames = {"Stock Acct ID",
                "Transaction Type",
                "Stock Symbol",
                "# of shares",
                "Price",
                "Date",
                "Total Earning/Loss"};
        Object[][] data1 = {
                {}
        };
        
        String[][] data = {{"Name",
            "address",
            "init balance",
            "final balance",
            "total earnings/loss",
            "commission paid"}};

		try {
			data = DataConnection.genMonthlyStatement(targetName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int nTrans = 0;
		try{ nTrans = DataConnection.getNumTrans2(targetName); } catch (SQLException e) { System.out.println(e.getMessage()); }
		String name = data[0][0];
		String email = data[1][0];
		data1 = new String[nTrans][7];
		for(int i=0; i<nTrans; i++){
			data1[i][0] = data[i+2][0]; // stockaccountid
			data1[i][1] = data[i+2][1]; // transaction type
			data1[i][2] = data[i+2][2]; // stock symbol
			data1[i][3] = data[i+2][3]; // number of shares
			data1[i][4] = data[i+2][4]; // price
			data1[i][5] = data[i+2][5]; // date
			data1[i][6] = data[i+2][6]; // earnings or losses
		}
		
		// TODO
		// 1. initial & final balance
		double initialBalance = 0;
		double finalBalance = 0;
		// 2. get total earning/loss, including interest, for current month (1 double)
		double totalEarnings = 0;
		// 3. total commission paid
		int commission = 0;
        
        final JTable table = new JTable(data1, columnNames);
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);



        //add( new JPanel() );

        setVisible( true );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }


}