package com.shervinshaikh.starsrus;
 
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReviewBoard extends JFrame
{
    public ReviewBoard(String movieName)
    {
        setTitle( "List of Reviews" );
        setSize( 350,300 );
        setResizable( false );
        String[] columnNames = {"Author",
                "Review"};
        String[][] data = {
                {},
        };
        try{
        	data = DataConnection.getMovieReviews(movieName);
        } catch (SQLException e){
        	System.out.println("ERROR");
        }
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