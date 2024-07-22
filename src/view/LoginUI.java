package src.view;

import src.business.UserController;
import src.core.Helper;
import src.entity.User;

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
    private UserController userController;

    public LoginUI() {

        this.userController = new UserController();
        this.add(container);
        this.setTitle("Customer Management System");
        this.setSize(400,400);
        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;

        this.setLocation(x,y);
        lgn_button.addActionListener(e->{
            System.out.println("Clicked");
            if(!Helper.isEmailValid(this.fld_mail.getText()))
            {
                Helper.showMessage( "Invalid Email");
            }
            else if(Helper.isFieldListEmpty(new JTextField[]{this.fld_mail,this.fld_password }))
            {
                Helper.showMessage("fill");

            }else {
                System.out.println("You can enter...");
                Helper.showMessage("done");
                User user = userController.findByLogIn(this.fld_mail.getText(), this.fld_password.getText());
                if(user == null) {
                    Helper.showMessage("The user is not found with the mail and password you entered.");
                }
                else{
                    System.out.println(user.toString());
                    this.dispose();
                    DashboardUI dashboardUI = new DashboardUI(user);
                }
            }


        });
    }
}
