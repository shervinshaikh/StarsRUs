package com.shervinshaikh.starsrus;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ActiveCustomerBoard extends JFrame
{
    public ActiveCustomerBoard()
    {
        setTitle( "Active Customers" );
        setSize( 350,300 );
        setResizable( false );
        String[] columnNames = {"Name"};
        Object[][] data = {{}};
        try{ data[0] = DataConnection.getActiveCustomers(); } catch(SQLException e) { System.out.println("ERROR unable to get active customers"); } 
        int size = data[0].length;
        Object[][] data2 = new Object[size][1];
        for(int i=0; i<size; i++){
        	data2[i][0] = data[0][i];
        }
        
        final JTable table = new JTable(data2, columnNames);
        table.setFillsViewportHeight(true);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        //Add the scroll pane to this panel.
        add(scrollPane);
        setVisible( true );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }
}