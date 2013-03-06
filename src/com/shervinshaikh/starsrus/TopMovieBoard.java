/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/5/13
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
 
 package com.shervinshaikh.starsrus;
import javax.swing.*;
public class TopMovieBoard extends JFrame
{

    public TopMovieBoard(int starting_date, int ending_date)
    {
        setTitle( "Creating a new Panel" );
        setSize( 350,300 );
        setResizable( false );

        String[] columnNames = {"Movie Name"};
        Object[][] data = {
                {"Kathy"},
                {"Jane"},
                {"Joe"}
        };

        final JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);



        //add( new JPanel() );

        setVisible( true );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
    }


}