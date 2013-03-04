package com.shervinshaikh.starsrus;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class ChildExample{

	JTextField usernameTxt;
	JPasswordField passwordTxt;
	JInternalFrame loginFrame;
	
	public static void main(String[] args){
		ChildExample ex1 = new ChildExample();
		ex1.go();
				
	
	}
	public void go(){
	
		
		JFrame mainFrame = new JFrame("Main");
		mainFrame.setSize(1000,600);
		loginFrame = new JInternalFrame("Login");
		loginFrame.setSize(400,200);
		mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
		JDesktopPane mainPanel = new JDesktopPane();
		JPanel loginPanel = new JPanel();
		
		JTextArea textArea = new JTextArea(25,25);
		usernameTxt = new JTextField(25);	
		passwordTxt = new JPasswordField(25);
		JLabel usernameLbl = new JLabel("Username: ");
		JLabel passwordLbl = new JLabel("Password: ");
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new loginButtonListener());

		mainPanel.add(textArea);
		mainPanel.add(loginFrame);
		String[] fruitOptions = {"Apple", "Apricot", "Banana"
	              ,"Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"};
		final JPanel comboPanel = new JPanel();
	      JLabel comboLbl = new JLabel("Fruits:");
	      JComboBox fruits = new JComboBox(fruitOptions);
	      
	      mainPanel.add(comboLbl);
	      mainPanel.add(fruits);
		
		
		loginPanel.add(usernameLbl);
		loginPanel.add(usernameTxt);
		loginPanel.add(passwordLbl);
		loginPanel.add(passwordTxt);
		loginPanel.add(loginButton);
		
		loginFrame.getContentPane().add(BorderLayout.CENTER,loginPanel);
		mainFrame.getContentPane().add(BorderLayout.CENTER,mainPanel);

		loginFrame.setVisible(true);					
		mainFrame.setVisible(true);		
						

	}



	public class loginButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			//if username and password is good hide child window
			loginFrame.setVisible(false);
				
			
		}
		
	}
}
