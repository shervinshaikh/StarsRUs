
 package com.shervinshaikh.starsrus;
 
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/6/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class BalanceTable extends JPanel {
    private boolean DEBUG = false;
    int taxid= 1022;

    public BalanceTable() {
        super(new GridLayout(1,0));

        String[] columnNames = {"Stock ID",
                "Num Owned", "Symbol"};

        Object[][] data = {
                {"YAHOO", "200", "YHO"}
        };
        
        int nAccounts = 0;
        try{ nAccounts = DataConnection.getNAccounts(taxid); } catch (SQLException e) { System.out.println("unable to get num accounts");} 
        //Object[][] data2;// = new Object[nAccounts][1];
       
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
        	data3[j][2] =data[i+2][0];
        	j++;
        }

        final JTable table = new JTable(data3, columnNames);
        //table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        table.setFillsViewportHeight(true);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}