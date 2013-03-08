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

	String name, email;
	String initialBalance, finalBalance, totalEarnings, commission;
    public MonthlyStatementBoard(String targetName)
    {
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
        String[][] data = {{}};
		try {
			data = DataConnection.genMonthlyStatement(targetName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int nTrans = 0;
		try{ nTrans = DataConnection.getNumTrans2(targetName); } catch (SQLException e) { System.out.println(e.getMessage()); }
		name = data[0][0];
		email = data[1][0];
		double interest = Double.parseDouble(data[0][4].toString());
		data1 = new String[nTrans][7];
		for(int i=0; i<nTrans; i++){
			data1[i][0] = data[i+2][0]; // stockaccountid
			data1[i][1] = data[i+2][1]; // transaction type
			data1[i][2] = data[i+2][2]; // stock symbol
			data1[i][3] = data[i+2][3]; // number of shares
			data1[i][4] = data[i+2][4]; // price
			data1[i][5] = data[i+2][5]; // date
			data1[i][6] = data[i+2][6]; // earnings or losses
			interest += Double.parseDouble(data[i+2][6].toString()); // add up all earnings to interest
		}
		// TODO
		// 1. initial & final balance
		initialBalance = "0";
		finalBalance = data[0][1].toString();
		// 2. get total earning/loss, including interest, for current month (1 double)
		totalEarnings = "" + interest;
		// 3. total commission paid
		commission = data[0][3].toString();
		
		JLabel nameLabel = new JLabel("Name: " + name );
        JLabel emailLabel = new JLabel("Email: " + email);
        JLabel iBalLabel = new JLabel("Initial Balance: "+initialBalance);
        JLabel fBalLabel = new JLabel("Final Balance: "+finalBalance);
        JLabel totEarningsLabel = new JLabel("Total Earnings/Losses: "+totalEarnings);
        JLabel commLabel = new JLabel("Commission: "+commission);
        
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