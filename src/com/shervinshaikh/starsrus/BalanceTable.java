package com.shervinshaikh.starsrus;
 
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BalanceTable extends JPanel {
	JTable table;
	
    public BalanceTable(int taxid) {
        super(new GridLayout(1,0));
        String[] columnNames = {"Stock ID",
                "Num Owned", "Symbol"};
        Object[][] data = {{}};
        int nAccounts = 0;
        try{ nAccounts = DataConnection.getNAccounts(taxid); } catch (SQLException e) { System.out.println("unable to get num accounts");} 
        try{
        	data = DataConnection.getBalances(taxid);
        } catch(SQLException e){ System.out.println("ERROR unable to get balances for account: " + taxid); }
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
        table = new JTable(data3, columnNames);
        //table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        table.setFillsViewportHeight(true);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    
	public void updateTable(int taxid){
		//get data again and remake table
		Object[][] data = {{}};
		int nAccounts = 0;
        try{ nAccounts = DataConnection.getNAccounts(taxid); } catch (SQLException e) { System.out.println("unable to get num accounts");} 
        try{
        	data = DataConnection.getBalances(taxid);
        } catch(SQLException e){ System.out.println("ERROR unable to get balances for account: " + taxid); }
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
        String[] columnNames = {"Stock ID",
                "Num Owned", "Symbol"};
        table = new JTable(data3, columnNames);
        table.setFillsViewportHeight(true);
	}
}