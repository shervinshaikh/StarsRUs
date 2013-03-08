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

public class RegisterFrame extends JFrame {
    public static void main(String[] args) {
        @SuppressWarnings("unused")
		RegisterFrame frameTabel = new RegisterFrame();
    }

    JLabel namel = new JLabel("Full name");
    JLabel statel = new JLabel("State");
    JLabel phonel = new JLabel("Phone");
    JLabel emaill = new JLabel("Email");
    JLabel taxidl = new JLabel("Tax id");
    JLabel userl = new JLabel("Username");
    JLabel passl = new JLabel("Password");
    JLabel addrl = new JLabel("Address");
    JLabel ssnl = new JLabel("Social Security No.");

    JButton bregister = new JButton("Register");
    JButton bback = new JButton("Back");
    JPanel panel = new JPanel();
    JTextField fullname = new JTextField(30);
    JTextField state = new JTextField(2);
    JTextField phoneno = new JTextField(10);
    JTextField email = new JTextField(30);
    JTextField taxid = new JTextField(10);
    JTextField user = new JTextField(20);
    JTextField addr = new JTextField(40);
    JTextField ssn = new JTextField(11);
    JPasswordField pass = new JPasswordField(30);

    RegisterFrame(){
        super("Register new account");
        setSize(300,450);
        setLocation(500,280);
        panel.setLayout (null);

        namel.setBounds(70,15,150,15);
        statel.setBounds(70,50,150,15);
        phonel.setBounds(70,85,150,15);
        emaill.setBounds(70,120,150,15);
        taxidl.setBounds(70,155,150,15);
        userl.setBounds(70,190,150,15);
        passl.setBounds(70,225,150,15);
        addrl.setBounds(70,260,150,15);
        ssnl.setBounds(70,295,150,15);

        fullname.setBounds(70,30,150,20);
        state.setBounds(70,65,150,20);
        phoneno.setBounds(70,100,150,20);
        email.setBounds(70,135,150,20);
        taxid.setBounds(70,170,150,20);
        user.setBounds(70,205,150,20);
        pass.setBounds(70,240,150,20);
        addr.setBounds(70,275,150,20);
        ssn.setBounds(70,310,150,20);

        bregister.setBounds(105,340,85,20);
        bback.setBounds(105,360,85,20);

        panel.add(namel);
        panel.add(statel);
        panel.add(phonel);
        panel.add(emaill);
        panel.add(taxidl);
        panel.add(userl);
        panel.add(passl);
        panel.add(addrl);
        panel.add(ssnl);

        panel.add(fullname);
        panel.add(email);
        panel.add(phoneno);
        panel.add(pass);
        panel.add(user);
        panel.add(state);
        panel.add(taxid);
        panel.add(addr);
        panel.add(ssn);

        panel.add(bregister);
        panel.add(bback);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionregister();
        actionback();
    }

    public void actionregister(){
        bregister.addActionListener(new ActionListener(){
            @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ae){
            	try {
					DataConnection.registerCustomer(Integer.parseInt(taxid.getText()), ssn.getText(), phoneno.getText(), 
							fullname.getText(), email.getText(), user.getText(),
							pass.getText(), addr.getText(), state.getText());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                JOptionPane.showMessageDialog(null,"Account Created!");
                Log logFace = new Log();
                logFace.setVisible(true);
                dispose();

            }
        });

    }
    
    public void actionback(){

        bback.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Log logFace = new Log();
                logFace.setVisible(true);
                dispose();
            }
        });
    }
    
}
