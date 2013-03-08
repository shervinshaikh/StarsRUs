package com.shervinshaikh.starsrus;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SimpleTableDemo extends JPanel {
    private boolean DEBUG = false;
    //int taxid = 1022;

    public SimpleTableDemo(int taxid) {
        super(new GridLayout(1,0));

        String[] columnNames = {"StockID",
                "type",
                "symbol",
                "# shares",
                "price",
                "date",
                "earnings"};

        String[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", "blah", "blah", "blah", "blah"},


        };
        
        try { data = DataConnection.getTransactionHistory(taxid); } catch (SQLException e) { System.out.println("ERROR getting transaction history"); }

        final JTable table = new JTable(data, columnNames);
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