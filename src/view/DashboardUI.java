package src.view;

import src.core.Helper;
import src.entity.User;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {

    private JPanel container;
    private User loggedUser = null;

    public DashboardUI(User user) {
        this.loggedUser = user;
        if(user == null) {
            Helper.showMessage("error");
            dispose();
        }
        this.add(container);
        this.setTitle("CUSTOMER MANAGEMENT SYSTEM");
        this.setSize(1000,500);

        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;

        this.setLocation(x,y);

    }
}
