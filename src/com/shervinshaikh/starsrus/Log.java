package com.shervinshaikh.starsrus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Log extends JFrame {

    public static void main(String[] args) {
        Log frameTabel = new Log();
    }

    JLabel userl = new JLabel("Username");
    JLabel passl = new JLabel("Password");

    JButton blogin = new JButton("Login");
    JButton bregister = new JButton("Register");
    JPanel panel = new JPanel();
    JTextField txuser = new JTextField(15);
    JPasswordField pass = new JPasswordField(15);

    Log(){
        super("Login Authentication");
        setSize(300,200);
        setLocation(500,280);
        panel.setLayout (null);

        userl.setBounds(70,15,150,15);
        passl.setBounds(70,50,150,15);

        txuser.setBounds(70,30,150,20);
        pass.setBounds(70,65,150,20);
        blogin.setBounds(105,100,85,20);
        bregister.setBounds(105,120,85,20);

        panel.add(userl);
        panel.add(passl);
        panel.add(blogin);
        panel.add(bregister);
        panel.add(txuser);
        panel.add(pass);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionlogin();
        actionreg();
    }

    public void actionlogin(){
        blogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String puname = txuser.getText();
                String ppaswd = pass.getText();
                
                //is the user a manager
                int userStatus=0;
				try {
					userStatus = DataConnection.validUser(puname,ppaswd);
				} catch (SQLException e) {
					e.printStackTrace();
				}
                
                if(userStatus!=0) {
                    
                	if(userStatus==1){
	                	TabbedPaneDemo regFace = new TabbedPaneDemo();
	                    regFace.setVisible(true);
	                    regFace.createAndShowGUI();
	                    dispose();
                	}
                	else if(userStatus==2){
                		ManagerPane regFace = new ManagerPane();
                		regFace.setVisible(true);
                		regFace.createAndShowGUI();
                		dispose();
                		
                	}
                } else {

                    JOptionPane.showMessageDialog(null,"Wrong Password / Username");
                    txuser.setText("");
                    pass.setText("");
                    txuser.requestFocus();
                }

            }
        });
    }

    public void actionreg() {
        bregister.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                registerframe regFace =new registerframe();
                regFace.setVisible(true);
                dispose();

            }
        });



    }
}
