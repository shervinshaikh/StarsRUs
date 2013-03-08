package com.shervinshaikh.starsrus;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReportBoard extends JFrame
{
    public ReportBoard()
    {
        setTitle( "Government DTER" );
        setSize( 350,300 );
        setResizable( false );
        String[] columnNames = {"Name",
                "Residence State",
                "Earnings"};
        Object[][] data = {{}};
        try { data = DataConnection.genDTER(); } catch(SQLException e) { System.out.println("ERROR unable to generate DTER"); }
        final JTable table = new JTable(data, columnNames);
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