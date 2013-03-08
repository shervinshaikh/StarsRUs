package com.shervinshaikh.starsrus;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/6/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */

public class ActiveCustomerBoard extends JFrame
{

    public ActiveCustomerBoard()
    {


        setTitle( "Active Customers" );
        setSize( 350,300 );
        setResizable( false );

        String[] columnNames = {"Name"};
        Object[][] data = {
                {}
        };
        
        try{ data[0] = DataConnection.getActiveCustomers(); } catch(SQLException e) { System.out.println("ERROR unable to get active customers"); }
                
        final JTable table = new JTable(data, columnNames);
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