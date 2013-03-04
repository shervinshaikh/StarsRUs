package com.shervinshaikh.starsrus;

/*
 * TabbedPaneDemo.java requires one additional file:
 *   images/middle.gif.
 */
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
 
public class TabbedPaneDemo extends JPanel {
    public TabbedPaneDemo() {
        super(new GridLayout(1, 1));
         
        // DEPOSIT PANEL
        JTabbedPane tabbedPane = new JTabbedPane();
        //ImageIcon icon = createImageIcon("images/middle.gif");
        //JComponent panel1 = makeTextPanel("Panel #1");
        
        JButton submitD = new JButton("Submit");
        JTextField amountD = new JTextField(20);
		//buyB.addActionListener(this);
        JPanel p1 = new JPanel();
        
        p1.add(amountD);
        p1.add(submitD);
        
        
        tabbedPane.addTab("Deposit", p1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        JButton submitW = new JButton("Submit");
        JTextField amountW = new JTextField(20);
		//buyB.addActionListener(this);
        JPanel p2 = new JPanel();
        
        p2.add(amountW);
        p2.add(submitW);
        
        
        // WITHDRAW PANEL
        //JComponent panel2 = makeTextPanel("Panel #2");
        tabbedPane.addTab("Withdraw", p2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
         
        
        // BUY PANEL
        JComponent panel3 = makeTextPanel("Panel #3");
        tabbedPane.addTab("Buy", panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
         
        
        // SELL PANEL
        JComponent panel4 = makeTextPanel("Panel #4");
        panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Sell", panel4);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
         
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
