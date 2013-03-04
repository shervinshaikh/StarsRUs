package com.shervinshaikh.starsrus;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Customer implements ActionListener {

	JButton depositB, withdrawB, buyB, submit;
	JPanel buttons;
	JPanel deposit;
	
	
	public static void main(String[] args) {
		Customer gui = new Customer();
		gui.go();

	}
	
	
	public void go(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		depositB = new JButton("Deposit");
		withdrawB = new JButton("Withdraw");
		buyB = new JButton("Buy");
		
		depositB.addActionListener(this);
		withdrawB.addActionListener(this);
		buyB.addActionListener(this);
		
		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.add(depositB);
		buttons.add(withdrawB);
		buttons.add(buyB);
		
		deposit = new JPanel();
		submit = new JButton("Submit");
		deposit.setLayout(new BoxLayout(deposit, BoxLayout.Y_AXIS));
		deposit.add(submit);
		
		frame.add(new TabbedPaneDemo(), BorderLayout.CENTER);
		//frame.getContentPane().add(BorderLayout.WEST, buttons);
		//frame.getContentPane().add(BorderLayout.CENTER, deposit);
		//frame.getContentPane().add(BorderLayout.SOUTH, withdrawB);
		//frame.getContentPane().add(BorderLayout.WEST, buyB);
		
		frame.pack();
		frame.setSize(300,300);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev){
		buyB.setText("clicked buy");
	}

}
