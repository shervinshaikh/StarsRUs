/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/6/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
 
package com.shervinshaikh.starsrus;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CusReportBoard extends JFrame
{

    public CusReportBoard(String name)
    {
        String[] columnNames = {"Stockid",
                "# of Shares",
                "Symbols"};
        Object[][] data = {
                {"A Big Cream", "ABC", new Integer(33)},
                {"kool kids klub", "KKK", new Integer (70)}
        };
        int nAccounts = 0;
        try{ nAccounts = DataConnection.getNAccounts2(name); } catch (SQLException e) { System.out.println("unable to get num accounts");}
        //Object[][] data2;
        
        try { data = DataConnection.getBalances2(name); } catch(SQLException e) { System.out.println("ERROR unable to create customer report"); }
        
        int nAccounts2 = (nAccounts - 2)/3;
        Object[][] data3 = new Object[nAccounts2][3];
        int j = 0;
        System.out.println("number of accounts: " + nAccounts);
        for(int i=2; i<nAccounts; i+=3){
        	data3[j][0] = data[i][0];
        	data3[j][1] = data[i+1][0];
        	System.out.println("shares: " + data3[j][1]);
        	data3[j][2] =data[i+2][0];
        	j++;
        }
        
        JLabel markIdLabel = new JLabel("Market ID: " + data[0][0]);
        JLabel balanceLabel = new JLabel("Balance: " + data[1][0]);
        setLayout(new GridLayout(3,1));
        //setLayout(null);

        setTitle( "Customer Report" );
        setSize( 600,300 );
        setResizable( true );
        
        final JTable table = new JTable(data3, columnNames);
        table.setBounds(0,0,300,300);
        markIdLabel.setBounds(0,0,100,20);
        balanceLabel.setBounds(0,20,100,20);

        add(markIdLabel);
        add(balanceLabel);
        add(table);

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