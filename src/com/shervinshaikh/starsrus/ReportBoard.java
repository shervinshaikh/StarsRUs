/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/6/13
 * Time: 4:01 PM
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
//        Object[][] data = {
//                {"Jack",new Integer(400000),new Integer(3),"CA"},
//                {"Kathy",new Integer(1200000),new Integer(5),"NV"}
//        };
        
        Object[][] data = {{}};
        try { data = DataConnection.genDTER(); } catch(SQLException e) { System.out.println("ERROR unable to generate DTER"); }
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