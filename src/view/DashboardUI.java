package src.view;

import src.business.CustomerController;
import src.core.Helper;
import src.entity.Customer;
import src.entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DashboardUI extends JFrame {

    private JPanel container;
    private JPanel panelTop;
    private JLabel lbl_top;
    private JButton EXITButton;
    private JTabbedPane pn_customer;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JComboBox comboBoxCustomerType;
    private JButton searchButton;
    private JButton clearButton;
    private JButton addNewButton;
    private JLabel lbl_fld_customer_name;
    private JLabel lbl_customer_type;
    private JTextField fld_customer_name_text;
    private User loggedUser = null;
    private CustomerController customerController;
    private DefaultTableModel tbmdl_customer = new DefaultTableModel();
    private JPopupMenu popup_customer = new JPopupMenu();

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

        ArrayList<Customer> customers = this.customerController.findAll();
        loadCustomerTable(customers);
        loadCustomerPopUpMenu();
        loadCustomerButtonEvent();

        this.comboBoxCustomerType.setModel(new DefaultComboBoxModel<>(Customer.CUSTYPE.values()));
        this.comboBoxCustomerType.setSelectedItem(null) ;

    }

    private void loadCustomerButtonEvent()
    {
        this. addNewButton.addActionListener(e -> {
            CustomerUI customerUI = new CustomerUI(new Customer());
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null)    ;
                }
            });

        } );

        this.searchButton.addActionListener(e -> {
            ArrayList<Customer> filteredCustomers = this.customerController.filterCustomer(  
                    this.fld_customer_name_text.getText() ,
                    (Customer.CUSTYPE) this.comboBoxCustomerType.getSelectedItem()
            ); 
            
            loadCustomerTable(filteredCustomers);  
        });


        this.clearButton.addActionListener(e -> {
             loadCustomerTable(null);
             this.fld_customer_name_text.setText(null);
              this.comboBoxCustomerType.setSelectedItem(null);   
        });
    }

    private void loadCustomerPopUpMenu(){

        this.tbl_customer.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_customer.rowAtPoint(e.getPoint());
                tbl_customer.setRowSelectionInterval(selectedRow,selectedRow); // to select the lines şn the table
            }
        });

        this.popup_customer.add("Update").addActionListener( e -> {
            int selectId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(),0).toString()); //mine was (int) tbl_customer.getValueAt(tbl_customer.getSelectedRow(),0);
            System.out.println(selectId);
            Customer editedCustomer = this.customerController.findByIdCustomer(selectId);
            CustomerUI customerUI = new CustomerUI(editedCustomer);
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null );
                }
            });

        });

        this.popup_customer.add("Delete").addActionListener(e ->{
            int selectId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(),0).toString());
            if(Helper.confirm("sure"))
            {
                if(this.customerController.delete(selectId)){
                    Helper.showMessage("success");
                    loadCustomerTable(null ); //updates table
                } else{
                    Helper.showMessage("error");
                }
            }
            System.out.println("Delete clicked!");
        } );

        this.tbl_customer.setComponentPopupMenu(this.popup_customer) ;
    }

    private void loadCustomerTable(ArrayList<Customer> customers) {
        Object[] columncustomer =   {"ID","Customer Name", "Customer Type ", "Phone", "Mail" , "Address" };

        if(customers == null  || customers.isEmpty()) {
            customers = this.customerController.findAll();
        }

        //to clear the table whole 
        DefaultTableModel clearModel = (DefaultTableModel) tbl_customer.getModel();
        clearModel.setRowCount(0);

        this.tbmdl_customer.setColumnIdentifiers(columncustomer);

        for(Customer customer : customers) {
            Object[] rowcustomer = {customer.getId(),
                    customer.getName(),
                    customer.getType(),
                    customer.getPhone(),
                    customer.getMail(),
                    customer.getAddress()
            };
            this.tbmdl_customer.addRow(rowcustomer);
        }

        this.tbl_customer.setModel(tbmdl_customer);
        this.tbl_customer.getTableHeader().setReorderingAllowed(false);
        this.tbl_customer.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_customer.setEnabled(false);
    }
}


//debug findall and load customers methods it updates and deletes but doesnt show