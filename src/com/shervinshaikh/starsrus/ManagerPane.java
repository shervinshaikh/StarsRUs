package com.shervinshaikh.starsrus;
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ManagerPane extends JPanel {
    JTabbedPane tabbedPane;
    JPanel p1;
    String selectedCustomer = "";
    SpinnerDateModel model;
    JComboBox stockNameList;
    JTextField inputPrice;

    @SuppressWarnings("deprecation")
	public ManagerPane(){
    	super(new GridLayout(1,1));

        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(600, 400));

        p1 = new JPanel();
        p1.setLayout(null);
        JButton addInterest = new JButton("Add Interest");
        addInterest.setBounds(0,20,150,20);
        addInterest.addActionListener(new addInterestListener());
        p1.add(addInterest);


        String[] customerList = { "Nivedh", "Sherv"};
        try { customerList = DataConnection.getCustomers(); } catch(SQLException e) { System.out.println("ERROR getting list of customers"); }
        JComboBox customerPicker = new JComboBox(customerList);
        customerPicker.addItemListener(new customerPickListener());
        customerPicker.setBounds(0,60,100,20);
        p1.add(customerPicker);
        JButton generateStatement = new JButton("Gen Monthly Statement");
        generateStatement.setBounds(0,80,220,20);
        generateStatement.addActionListener(new genStatementListener());
        p1.add(generateStatement);
        selectedCustomer = customerPicker.getItemAt(0).toString();
        JButton listActiveCustomers = new JButton("List Active");
        listActiveCustomers.setBounds(0,160,200,20);
        listActiveCustomers.addActionListener(new listActiveListener());
        p1.add(listActiveCustomers);
        JButton genDTER = new JButton("Gen DTER");
        genDTER.setBounds(0,200,200,20);
        genDTER.addActionListener(new genDTERListener());
        p1.add(genDTER);
        JButton makeCustomerReport = new JButton("Gen customer report");
        makeCustomerReport.setBounds(0,100,200,20);
        makeCustomerReport.addActionListener(new makeReportListener());
        p1.add(makeCustomerReport);
        JButton deleteTrans = new JButton("Delete Transactions");
        deleteTrans.setBounds(0,240,200,20);
        deleteTrans.addActionListener(new deleteTransListener());
        p1.add(deleteTrans);



        //ADD TEST FUNCTIONS

        JButton openMarket = new JButton ("Open Market");
        JButton closeMarket = new JButton ("Close Market");
        openMarket.setBounds(400,20,150,20);
        closeMarket.setBounds(400,40,150,20);
        openMarket.addActionListener(new openMarketListener());
        closeMarket.addActionListener(new closeMarketListener());
        p1.add(openMarket);
        p1.add(closeMarket);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String tempdate="";
        
        try{tempdate = DataConnection.getTodaysDate();} catch(SQLException e) { System.out.println("ERROR getting date"); }
        
        String tempyear = tempdate.substring(9,13);
        String tempmonth = tempdate.substring(14,16);
        String tempday = tempdate.substring(17,19);
        
        date = new Date(Integer.parseInt(tempyear),Integer.parseInt(tempmonth)-1,Integer.parseInt(tempday));
        
        
        
        System.out.println("tempdate: " + date);

        model = new SpinnerDateModel();
        model.setValue(date);
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd-MM-yy"));

        spinner.setBounds(400,80,120,20);
        spinner.addChangeListener(new dateListener());
        p1.add(spinner);
        JButton submitNewDate = new JButton("Set Date");
        submitNewDate.setBounds(400,100,100,20);
        submitNewDate.addActionListener(new dateButtonListener());
        p1.add(submitNewDate);


        String[] stockSymbols = { "Yahoo", "Google", "Apple", "Qualcomm", "Microsoft" };
        try { stockSymbols = DataConnection.getStockSymbols(); } catch(SQLException e) {System.out.println("ERROR unable to get stock symbols");}
        
        stockNameList = new JComboBox(stockSymbols);
        stockNameList.setBounds(400,180,100,20);
        stockNameList.addItemListener(new StockNameListener());
        p1.add(stockNameList);

        inputPrice = new JTextField(20);
        inputPrice.setBounds(400,200,100,20);
        p1.add(inputPrice);
        JButton setPrice = new JButton("Set Price");
        setPrice.setBounds(400,220,100,20);
        setPrice.addActionListener(new SetPriceListener());
        p1.add(setPrice);

        tabbedPane.addTab("Manager Interface", p1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        //Add the tabbed pane to this panel.
        add(tabbedPane);
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    class addInterestListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
        	try{ DataConnection.addInterest(); } catch(SQLException e) {System.out.println("ERROR unable to add interest"); }
            JOptionPane.showMessageDialog(null, "Add Interest!");
        }
    }
    class customerPickListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event){
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object item = event.getItem();
                selectedCustomer = item.toString();
            }
        }
    }
    class genStatementListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            @SuppressWarnings("unused")
			MonthlyStatementBoard msBoard = new MonthlyStatementBoard(selectedCustomer);
            //JOptionPane.showMessageDialog(null, "Generate Statement!");
        }
    }
    class listActiveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
        	@SuppressWarnings("unused")
            ActiveCustomerBoard activeTab = new ActiveCustomerBoard();

            //JOptionPane.showMessageDialog(null,"List stuff!");
        }
    }
    class genDTERListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
        	@SuppressWarnings("unused")
            ReportBoard repBoard = new ReportBoard();

            //JOptionPane.showMessageDialog(null,"gen DTER!");
        }
    }
    class makeReportListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
        	@SuppressWarnings("unused")
            CusReportBoard cusBoard = new CusReportBoard(selectedCustomer);
            //JOptionPane.showMessageDialog(null,"make report!");
        }
    }
    class deleteTransListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
        	try { DataConnection.deleteTransactions(); } catch(SQLException e) { System.out.println(e.getMessage()); }
            JOptionPane.showMessageDialog(null,"All Transactions Deleted!");
        }
    }

    //TEST FUNCTIONS
    class openMarketListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
        	try { DataConnection.openMarket(); } catch(SQLException e){ System.out.println("ERROR unable to open market"); }
            JOptionPane.showMessageDialog(null,"Market Opened!");
        }
    }
    class closeMarketListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
        	try { DataConnection.closeMarket(); } catch(SQLException e) { System.out.println("ERROR unable to close market"); }
            JOptionPane.showMessageDialog(null,"Market Closed");
        }
    }
    class dateListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
        	@SuppressWarnings("unused")
            Date date = (Date) ((JSpinner) e.getSource()).getValue();
            //System.out.println("Date: " + date);
            //for (int i = 0; i < labels.length; i++) {
            //    labels[i].setText(formats[i].format(date));
            //}
        }

    }

    class dateButtonListener implements ActionListener{
        @SuppressWarnings("deprecation")
		@Override
        public void actionPerformed(ActionEvent arg0){
        	Date date = (Date) model.getValue();
            //SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
            //String sqlDate = sdf.format(date);
            //System.out.println("date: " + sqlDate);
            try{ DataConnection.setDate(date); } catch(SQLException e) { System.out.println(e.getMessage()); }
            JOptionPane.showMessageDialog(null,"Changed date to " + date.toLocaleString().substring(0,13));
        }

    }

    class StockNameListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event){
//            if (event.getStateChange() == ItemEvent.SELECTED) {
//                Object item = event.getItem();
//            }
        }
    }
    class SetPriceListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
        	String stock = stockNameList.getSelectedItem().toString();
        	double price = Double.parseDouble(inputPrice.getText());
        	
        	try{ DataConnection.setStockPrice(stock, price); } catch(SQLException e) { System.out.println(e.getMessage()); }
        	
            JOptionPane.showMessageDialog(null,"Price of " + stock + " changed to $" + price);
        }
    }


    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ManagerPane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new ManagerPane(), BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }



    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}


