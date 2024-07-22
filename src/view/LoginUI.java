package src.view;

import src.core.Helper;

import javax.swing.*;
import java.awt.*;

public class LoginUI  extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_panel;
    private JPanel pnl_bottom;
    private JLabel lbl_mail;
    private JTextField fld_mail;
    private JPasswordField fld_password;
    private JLabel lbl_pass;
    private JButton lgn_button;

    public LoginUI() {
         this.add(container);
         this.setTitle("Customer Management System");
         this.setSize(400,400);
         this.setVisible(true);
         int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
         int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;

         this.setLocation(x,y);
        lgn_button.addActionListener(e->{
            System.out.println("Clicked");
            if(Helper.isFieldListEmpty(new JTextField[]{this.fld_mail,this.fld_password }))
            {
                 System.out.println("Please fill all of the areas. ");
            }else {
                System.out.println("You can enter...");
                if(!Helper.isEmailValid(this.fld_mail.getText()))
                {
                    System.out.println("Please enter a valid email address");
                }
            }


        });
    }
}
