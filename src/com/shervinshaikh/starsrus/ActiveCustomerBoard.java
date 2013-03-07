import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        String[] columnNames = {"Name",
                "Buy/sell",
                "Price",
                "Num Shares"};
        Object[][] data = {
                {"Jack","Buy",new Integer(33),new Integer(100)},
                {"Kathy","Sell",new Integer(65),new Integer(250)}
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
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }


}