/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/6/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
 
 package com.shervinshaikh.starsrus;
import javax.swing.*;
import java.awt.*;

public class CusReportBoard extends JFrame
{

    public CusReportBoard()
    {

        JLabel markIdLabel = new JLabel("Market ID: ");
        JLabel balanceLabel = new JLabel("Balance: ");
        setLayout(new GridLayout(3,1));
        //setLayout(null);

        setTitle( "Customer Report" );
        setSize( 600,300 );
        setResizable( true );

        String[] columnNames = {"Stockid",
                "Symbol",
                "Shares"};
        Object[][] data = {
                {"A Big Cream", "ABC", new Integer(33)},
                {"kool kids klub", "KKK", new Integer (70)}
        };
        final JTable table = new JTable(data, columnNames);
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