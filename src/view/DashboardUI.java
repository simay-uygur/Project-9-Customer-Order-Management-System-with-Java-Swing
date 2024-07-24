package src.view;

import src.business.CustomerController;
import src.core.Helper;
import src.entity.Customer;
import src.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DashboardUI extends JFrame {

    private JPanel container;
    private JPanel panelTop;
    private JLabel lbl_top;
    private JButton EXITButton;
    private JTabbedPane pn_customer;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JButton button1;
    private JComboBox comboBox1;
    private JButton searchButton;
    private JButton clearButton;
    private JButton addNewButton;
    private JLabel lbl_fld_customer_name;
    private JLabel lbl_customer_type;
    private User loggedUser = null;
    private CustomerController customerController;

    public DashboardUI(User user) {
        this.loggedUser = user;
        this.customerController = new CustomerController();
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

        this.lbl_top.setText("Welcome "+loggedUser.getName()) ;

        this.EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 dispose();
                 LoginUI loginUI = new LoginUI();
            }
        });

        //loadCustomerTable();

    }

    private void loadCustomerTable(ArrayList<Customer> customers) {
        Object[] columncustomer =   {"ID", "Customer Type ",   };
    }
}
