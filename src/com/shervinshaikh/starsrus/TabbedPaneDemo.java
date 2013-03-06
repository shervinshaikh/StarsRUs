
/*
 * TabbedPaneDemo.java requires one additional file:
 *   images/middle.gif.
 */

package com.shervinshaikh.starsrus;
 
import java.awt.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabbedPaneDemo extends JPanel {
    JTabbedPane tabbedPane;
    JTextField amountD;
    StockTable stockInfoPane;
    JComponent panel7;
    int first_top_date=0;
    int second_top_date=0;


    public TabbedPaneDemo() {

        super(new GridLayout(1, 1));


        // DEPOSIT PANEL
        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(600, 400));
        //set size
        //tabbedPane.setMinimumSize(new Dimension(800,500));

        //ImageIcon icon = createImageIcon("images/middle.gif");
        //JComponent panel1 = makeTextPanel("Panel #1");
        JButton submitD = new JButton("Submit");
        amountD = new JTextField(20);
        submitD.addActionListener(new DepositListener());

        JPanel p1 = new JPanel();
        p1.add(amountD);
        p1.add(submitD);


        tabbedPane.addTab("Deposit", p1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);



        // WITHDRAW PANEL
        JButton submitW = new JButton("Submit");
        JTextField amountW = new JTextField(20);

        submitW.addActionListener(new WithdrawListener());

        JPanel p2 = new JPanel();
        p2.add(amountW);
        p2.add(submitW);

        //JComponent panel2 = makeTextPanel("Panel #2");
        tabbedPane.addTab("Withdraw", p2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);



        // BUY PANEL
        //JComponent panel3 = makeTextPanel("Panel #3");
        JPanel p3 = new JPanel();
        //p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        p3.setLayout(null);
        JTextField sharesB = new JTextField(20);
        JLabel buylabel = new JLabel("# Shares:");
        //sharesB.setText("0");

        String [] stockSymbols = {"GOOG", "AAPL", "YAHOO"}; // Get values from the database
        JList list = new JList(stockSymbols);
        JScrollPane symbolScroll = new JScrollPane(list);
        symbolScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        symbolScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        list.setVisibleRowCount(4);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton purchase = new JButton("Purchase");
        purchase.addActionListener(new BuyListener());

        sharesB.setBounds(105,50, 60, 20);
        buylabel.setBounds(50,50,55,20);
        symbolScroll.setBounds(50,75,350,150);
        purchase.setBounds(410,180,90,30);

        p3.add(buylabel);
        p3.add(sharesB);
        p3.add(symbolScroll);
        p3.add(purchase);

        tabbedPane.addTab("Buy", p3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);


        // SELL PANEL
        JComponent panel4 = makeTextPanel("Panel #4");
        panel4.setPreferredSize(new Dimension(410, 50));

        panel4.setLayout(null);
        JTextField sharesS = new JTextField(20);
        JLabel selllabel = new JLabel("# Shares:");
        //sharesB.setText("0");

        String [] userSymbols = {"GOOG", "AAPL", "YAHOO"}; // Get values of stocks user owns from database
        JList list2 = new JList(userSymbols);
        JScrollPane symbolScroll2 = new JScrollPane(list2);
        symbolScroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        symbolScroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        list2.setVisibleRowCount(4);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton sells = new JButton("Sell");
        purchase.addActionListener(new BuyListener());

        sharesS.setBounds(105,50, 60, 20);
        selllabel.setBounds(50,50,55,20);
        symbolScroll2.setBounds(50,75,350,150);
        sells.setBounds(410,180,90,30);

        panel4.add(selllabel);
        panel4.add(sharesS);
        panel4.add(symbolScroll2);
        panel4.add(sells);



        tabbedPane.addTab("Sell", panel4);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);



        // SHOW BALANCE
        JComponent panel5 = makeTextPanel("Panel #5");
        panel5.setPreferredSize(new Dimension(410, 50));
        panel5.setLayout(null);

        JLabel labelUserId = new JLabel("User ID: ");
        JLabel labelMarketBal = new JLabel("Market Balance: ");
        JLabel labelStockBal = new JLabel("Stock Balance: ");

        labelUserId.setBounds(20,30, 150, 20);
        labelMarketBal.setBounds(20,55,150,20);
        labelStockBal.setBounds(20,80,150,20);

        panel5.add(labelUserId);
        panel5.add(labelMarketBal);
        panel5.add(labelStockBal);

        tabbedPane.addTab("Show Balance", panel5);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);


        // STOCK HISTORY
        JComponent panel6 = makeTextPanel("Panel #6");
        panel6.setPreferredSize(new Dimension(410, 50));

        panel6.setLayout(null);

        SimpleTableDemo newContentPane = new SimpleTableDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        panel6.add(newContentPane);
        newContentPane.setBounds(0,0,400,300);
        //frame.setContentPane(newContentPane);

        //Display the window.
        //frame.pack();
        //frame.setVisible(true);





        tabbedPane.addTab("Stock History", panel6);
        tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);


        // STOCK PROFILE
        panel7 = makeTextPanel("Panel #7");
        panel7.setPreferredSize(new Dimension(410, 50));
        panel7.setLayout(null);

        JLabel selectStockLabel = new JLabel("Select stock:");

        String[] petStrings = { "Yahoo", "Google", "Apple", "Qualcomm", "Microsoft" };

        stockInfoPane = new StockTable(111,111,"a","b","c","d","e","f");
        stockInfoPane.setOpaque(true); //content panes must be opaque
        panel7.add(stockInfoPane);
        stockInfoPane.setBounds(0, 60, 600, 300);

        //Create the combo box, select item at index 4.
        //Indices start at 0, so 4 specifies the pig.

        JComboBox stockInfoList = new JComboBox(petStrings);

        stockInfoList.addItemListener(new StockSelect());

        stockInfoList.setSelectedIndex(4);
        //petList.addActionListener(this);

        selectStockLabel.setBounds(20,0,80,20);
        stockInfoList.setBounds(20, 20, 80, 20);
        panel7.add(stockInfoList);
        panel7.add(selectStockLabel);

        tabbedPane.addTab("Stock Info", panel7);
        tabbedPane.setMnemonicAt(6, KeyEvent.VK_7);



        // MOVIE PROFILE
        JComponent panel8 = makeTextPanel("Panel #8");
        panel8.setPreferredSize(new Dimension(410, 50));
        panel8.setLayout(null);

        String[] movieOptions = { "Yahoo", "Google", "Apple", "Qualcomm", "Microsoft" };

        JComboBox moviePicker = new JComboBox(movieOptions);
        moviePicker.addItemListener(new MoviePickListener());
        moviePicker.setBounds(50,0,100,20);
        JLabel movTitleLabel = new JLabel("Title:");
        movTitleLabel.setBounds(0,0,50,20);
        JLabel movProdYear = new JLabel("Prod year:");
        movProdYear.setBounds(0,20,70,20);
        JLabel movRanking = new JLabel("Ranking:");
        movRanking.setBounds(0,40,50,20);



        JButton viewReviews = new JButton("Get Reviews");
        viewReviews.addActionListener(new reviewButtonListener());
        viewReviews.setBounds(0, 260, 120, 20);


        JButton viewTopMovies = new JButton("Top Movies");
        viewTopMovies.addActionListener(new topMovButtonListener());
        viewTopMovies.setBounds(0,300,120,20);

        //top movies stuff
        JLabel yearFromLabel = new JLabel("From:");
        yearFromLabel.setBounds(0,280,50,20);
        panel8.add(yearFromLabel);
        JLabel yearToLabel = new JLabel ("To:");
        yearToLabel.setBounds(120,280,50,20);
        panel8.add(yearToLabel);

        int currentYear = 2013;

        JSpinner sp1 = new JSpinner(new SpinnerNumberModel(currentYear, currentYear-100,currentYear+100,1));
        JSpinner sp2 = new JSpinner(new SpinnerNumberModel(currentYear,currentYear-100,currentYear+100,1));
        //If we're cycling, hook this model up to the month model.
        sp1.setBounds(50,280,60,20);
        sp2.setBounds(160,280,60,20);
        sp1.addChangeListener(new firstYear());
        sp2.addChangeListener(new secondYear());
        panel8.add(sp1);
        panel8.add(sp2);


        panel8.add(viewTopMovies);
        panel8.add(viewReviews);
        panel8.add(moviePicker);
        panel8.add(movTitleLabel);
        panel8.add(movProdYear);
        panel8.add(movRanking);



        tabbedPane.addTab("Movie Info", panel8);
        tabbedPane.setMnemonicAt(7, KeyEvent.VK_8);




        //Add the tabbed pane to this panel.
        add(tabbedPane);
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


    // LISTENER CLASSES
    class DepositListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Call a function that accesses the database and adds money to account
            int id = 1; //get actual ID
            double amount = Double.parseDouble(amountD.getText());

           // CONNECTION AND DATA INPUT GOES HERE

           /* try {
                //DataConnection.depositMoney(id, amount);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }*/
            JOptionPane.showMessageDialog(null, "Deposit Done!");
        }

    }

    class WithdrawListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // call function to subtract money from account (if possible)
            JOptionPane.showMessageDialog(null, "Withdraw Done!");
        }

    }

    class BuyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            JOptionPane.showMessageDialog(null, "Purchase Done!");

        }

    }
    class reviewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0){
            ReviewBoard revBoard = new ReviewBoard();
            // DO STUFF HERE
        }
    }

    class topMovButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0){
            TopMovieBoard topBoard = new TopMovieBoard(first_top_date,second_top_date);
            //do stuff here
        }
    }

    class MoviePickListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event){
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object item = event.getItem();
            }
        }
    }
    class firstYear implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e){
            JSpinner spinner = ( JSpinner ) e.getSource();
            SpinnerModel spinnerModel = spinner.getModel();
            //System.out.println(spinnerModel.getValue());
            first_top_date = Integer.parseInt(spinnerModel.getValue().toString());
            //System.out.println(first_top_date);
        }
    }
    class secondYear implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e){
            JSpinner spinner = ( JSpinner ) e.getSource();
            SpinnerModel spinnerModel = spinner.getModel();
            System.out.println(spinnerModel.getValue());
            second_top_date = Integer.parseInt(spinnerModel.getValue().toString());
        }
    }

    class StockSelect implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event){
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object item = event.getItem();
                //JOptionPane.showMessageDialog(null, "Something happened!");
                // do something with object
                if(item.toString() == "Apple"){
                    StockTable blah = new StockTable(30,22,"a","b","c","d","e","f");
                    blah.setOpaque(true); //content panes must be opaque
                    panel7.remove(stockInfoPane);
                    panel7.add(blah);
                    blah.setBounds(0, 60, 600, 300);
                }
                if(item.toString() == "Microsoft"){
                    StockTable blah = new StockTable(11,55,"a","b","c","d","e","f");
                    blah.setOpaque(true); //content panes must be opaque
                    panel7.remove(stockInfoPane);
                    panel7.add(blah);
                    blah.setBounds(0, 60, 600, 300);
                }
                //if(item.toString() != null)
                //    JOptionPane.showMessageDialog(null, item.toString());
            }
        }
    }





    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new TabbedPaneDemo(), BorderLayout.CENTER);

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
