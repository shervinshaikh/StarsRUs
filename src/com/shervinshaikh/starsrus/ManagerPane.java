/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/6/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
 package com.shervinshaikh.starsrus;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ManagerPane extends JPanel {
    JTabbedPane tabbedPane;

    public ManagerPane(){

        super(new GridLayout(1,1));

        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(600, 400));

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        JButton addInterest = new JButton("Add Interest");
        addInterest.setBounds(0,20,100,20);
        addInterest.addActionListener(new addInterestListener());
        p1.add(addInterest);


        String[] customerList = { "Nivedh", "Sherv"};
        JComboBox customerPicker = new JComboBox(customerList);
        customerPicker.addItemListener(new customerPickListener());
        customerPicker.setBounds(0,60,100,20);
        p1.add(customerPicker);
        JButton generateStatement = new JButton("Gen Monthly Statement");
        generateStatement.setBounds(0,80,200,20);
        generateStatement.addActionListener(new genStatementListener());
        p1.add(generateStatement);
        JButton listActiveCustomers = new JButton("List Active");
        listActiveCustomers.setBounds(0,160,100,20);
        listActiveCustomers.addActionListener(new listActiveListener());
        p1.add(listActiveCustomers);
        JButton genDTER = new JButton("Gen DTER");
        genDTER.setBounds(0,200,100,20);
        genDTER.addActionListener(new genDTERListener());
        p1.add(genDTER);
        JButton makeCustomerReport = new JButton("Gen customer report");
        makeCustomerReport.setBounds(0,100,200,20);
        makeCustomerReport.addActionListener(new makeReportListener());
        p1.add(makeCustomerReport);
        JButton deleteTrans = new JButton("Delete Transactions");
        deleteTrans.setBounds(0,120,200,20);
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
        SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(date);
        JSpinner spinner = new JSpinner(model);
        spinner.setBounds(400,80,120,20);
        spinner.addChangeListener(new dateListener());
        p1.add(spinner);
        JButton submitNewDate = new JButton("Set Date");
        submitNewDate.setBounds(400,100,100,20);
        submitNewDate.addActionListener(new dateButtonListener());
        p1.add(submitNewDate);

        tabbedPane.addTab("Main menu", p1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);









        //Add the tabbed pane to this panel.
        add(tabbedPane);
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    class addInterestListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            JOptionPane.showMessageDialog(null, "Add Interest!");
        }
    }
    class customerPickListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event){
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object item = event.getItem();
            }
        }
    }
    class genStatementListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            JOptionPane.showMessageDialog(null, "Generate Statement!");
        }
    }
    class listActiveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            JOptionPane.showMessageDialog(null,"List stuff!");
        }
    }
    class genDTERListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            JOptionPane.showMessageDialog(null,"gen DTER!");
        }
    }
    class makeReportListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            JOptionPane.showMessageDialog(null,"make report!");
        }
    }
    class deleteTransListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            JOptionPane.showMessageDialog(null,"delete trans!");
        }
    }

    //TEST FUNCTIONS
    class openMarketListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            JOptionPane.showMessageDialog(null,"Open market!");
        }
    }
    class closeMarketListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            JOptionPane.showMessageDialog(null,"Close market!");
        }
    }
    class dateListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            Date date = (Date) ((JSpinner) e.getSource()).getValue();
            //for (int i = 0; i < labels.length; i++) {
            //    labels[i].setText(formats[i].format(date));
            //}
        }

    }

    class dateButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            JOptionPane.showMessageDialog(null,"Change date!");
        }

    }


    private static void createAndShowGUI() {
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