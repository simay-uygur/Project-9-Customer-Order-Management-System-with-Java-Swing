package src.view;

import src.business.CustomerController;
import src.core.Helper;
import src.entity.Customer;

import javax.swing.*;
import java.awt.*;


public class CustomerUI extends JFrame {

    private JPanel container;
    private JTextField fld_name;
    private JComboBox<Customer.CUSTYPE> comboBox_type;
    private JTextField fld_phone;
    private JTextField fld_mail;
    private JTextArea txtarea_address;
    private JButton btn_create_change;
    private JLabel lbl_name;
    private JLabel lbl_type;
    private JLabel lbl_phone;
    private JLabel lbl_mail;
    private JLabel lbl_address;
    private JLabel lbl_title;
    private Customer customer;
    private CustomerController customerController;


    public CustomerUI(Customer customer){

        this.customer = customer;
        this.customerController = new CustomerController();

        this.add(container);
        this.setTitle("Add/Edit Customer");
        this.setSize(1000,500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;
        this.setLocation(x,y);
        this.setVisible(true);

        this.comboBox_type.setModel(new DefaultComboBoxModel<>(Customer.CUSTYPE.values()));

        if(this.customer.getId() == 0){
            this.lbl_title.setText("Add Customer");
        } else {
            this.lbl_title.setText("Edit Customer");
            this.fld_name.setText(this.customer.getName());
            this.fld_phone.setText(this.customer.getPhone());
            this.fld_mail.setText(this.customer.getMail());
            this.txtarea_address.setText(this.customer.getAddress());
            this.comboBox_type.getModel().setSelectedItem(this.customer.getType()) ;
        }

        this.btn_create_change.addActionListener(e ->
        {
            JTextField[] checkList = {this.fld_name, this.fld_mail};
            if(Helper.isFieldListEmpty(checkList)) {
                Helper.showMessage("fill");
            }
            else if(!Helper.isFieldEmpty(this.fld_name) && !Helper.isEmailValid(this.fld_mail.getText())) {
                Helper.showMessage("Please enter a valid email address");
            } else {
                boolean result = false;
                this.customer.setName(this.fld_name.getText());
                this.customer.setPhone(this.fld_phone.getText());
                this.customer.setMail(this.fld_mail.getText());
                this.customer.setAddress(this.txtarea_address.getText());
                this.customer.setType((Customer.CUSTYPE) this.comboBox_type.getSelectedItem());

                if(this.customer.getId() == 0){
                    result = this.customerController.save(customer);
                } else {
                     result = this.customerController.update(customer);
                }

                if(result)
                {
                    Helper.showMessage("done");
                    dispose();
                } else {
                    Helper.showMessage("error");
                }

            }
        });
    }
}
