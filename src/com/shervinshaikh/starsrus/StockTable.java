package com.shervinshaikh.starsrus;

/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/5/13
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
 
 
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StockTable extends JPanel {
    private boolean DEBUG = false;
    Object [][] data;
    JTable table;
    String[] columnNames = {"Current Price",
            "Close Price",
            "Name",
            "DoB",
            "Movie",
            "Role",
            "Year",
            "Contract"};

    public StockTable(String x, String y, String a, String b, String c, String d, String e, String f) {
        super(new GridLayout(1,0));
        
//        Object [][] newdata = {
//                {si},
//
//        };


        Object [][] newdata = {
                {x,y, a, b,c,d,e,f},

        };
        data = newdata;
        for(int i=0; i<8; i++){
        	System.out.println("0," + i + ": " + data[0][i]);
        }
        
        data = newdata;

        table = new JTable(data, columnNames);
        //table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        table.setFillsViewportHeight(true);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }



    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */


}