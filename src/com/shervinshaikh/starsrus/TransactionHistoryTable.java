package com.shervinshaikh.starsrus;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TransactionHistoryTable extends JPanel {

    public TransactionHistoryTable(int taxid) {
        super(new GridLayout(1,0));

        String[] columnNames = {"StockID",
                "type",
                "symbol",
                "# shares",
                "price",
                "date",
                "earnings"};
        String[][] data = {{}};
        
        try { data = DataConnection.getTransactionHistory(taxid); } catch (SQLException e) { System.out.println("ERROR getting transaction history"); }

        final JTable table = new JTable(data, columnNames);
        //table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
}