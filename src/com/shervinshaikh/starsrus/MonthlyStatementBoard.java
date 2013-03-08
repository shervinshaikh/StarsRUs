/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/6/13
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
 
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

public class MonthlyStatementBoard extends JFrame
{

    public MonthlyStatementBoard(String targetName)
    {


        setTitle( "Monthly Statement" );
        setSize( 350,300 );
        setResizable( false );

        String[] columnNames = {"Name",
                "address",
                "init balance",
                "final balance",
                "total earnings/loss",
                "commission paid"};
        Object[][] data1 = {
                {"Jack","12345 blah lane", new Integer(200), new Integer(300), new Integer(100),new Integer(20)}
        };
        
        String[][] data = {{"Name",
            "address",
            "init balance",
            "final balance",
            "total earnings/loss",
            "commission paid"}};
        System.out.println("name of person: " + targetName);
		try {
			data = DataConnection.genMonthlyStatement(targetName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        final JTable table = new JTable(data1, columnNames);
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