package com.shervinshaikh.starsrus;
 
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StockTable extends JPanel {
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

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
}