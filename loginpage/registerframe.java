/**
 * Created with IntelliJ IDEA.
 * User: Sprite
 * Date: 3/3/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class registerframe extends JFrame {
    public static void main(String[] args) {
        registerframe frameTabel = new registerframe();
    }


    JLabel namel = new JLabel("Full name");
    JLabel statel = new JLabel("State");
    JLabel phonel = new JLabel("Phone");
    JLabel emaill = new JLabel("Email");
    JLabel taxidl = new JLabel("Tax id");
    JLabel userl = new JLabel("Username");
    JLabel passl = new JLabel("Password");

    JButton bregister = new JButton("Register");
    JButton bback = new JButton("Back");
    JPanel panel = new JPanel();
    JTextField fullname = new JTextField(15);
    JTextField state = new JTextField(2);
    JTextField phoneno = new JTextField(10);
    JTextField email = new JTextField(20);
    JTextField taxid = new JTextField(15);
    JTextField user = new JTextField(15);
    JPasswordField pass = new JPasswordField(15);

    registerframe(){
        super("Register new account");
        setSize(300,400);
        setLocation(500,280);
        panel.setLayout (null);

        namel.setBounds(70,15,150,15);
        statel.setBounds(70,50,150,15);
        phonel.setBounds(70,85,150,15);
        emaill.setBounds(70,120,150,15);
        taxidl.setBounds(70,155,150,15);
        userl.setBounds(70,190,150,15);
        passl.setBounds(70,225,150,15);

        fullname.setBounds(70,30,150,20);
        state.setBounds(70,65,150,20);
        phoneno.setBounds(70,100,150,20);
        email.setBounds(70,135,150,20);
        taxid.setBounds(70,170,150,20);
        user.setBounds(70,205,150,20);
        pass.setBounds(70,240,150,20);
        bregister.setBounds(105,280,85,20);
        bback.setBounds(105,300,85,20);

        panel.add(namel);
        panel.add(statel);
        panel.add(phonel);
        panel.add(emaill);
        panel.add(taxidl);
        panel.add(userl);
        panel.add(passl);

        panel.add(fullname);
        panel.add(email);
        panel.add(phoneno);
        panel.add(pass);
        panel.add(user);
        panel.add(state);
        panel.add(taxid);
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
            public void actionPerformed(ActionEvent ae){


                // DO SOMETHING HERE
                // AKA: register account into DATABASE

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
