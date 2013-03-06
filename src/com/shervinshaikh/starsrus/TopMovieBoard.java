/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/5/13
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
 
 package com.shervinshaikh.starsrus;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
public class TopMovieBoard extends JFrame
{

    public TopMovieBoard(int starting_date, int ending_date)
    {
        setTitle( "Creating a new Panel" );
        setSize( 350,300 );
        setResizable( false );

        String[] columnNames = {"Movie Name"};
        String[][] data = {
                {"Kathy"},
                {"Jane"},
                {"Joe"}
        };
        
        
       try{
        	data = DataConnection.topMovies(starting_date, ending_date);
        } catch (SQLException e){
            System.out.println("ERROR");
        }
        
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