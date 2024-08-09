package src.view;

import src.business.BasketController;
import src.business.CustomerController;
import src.business.ProductController;
import src.core.Helper;
import src.core.Item;
import src.entity.Basket;
import src.entity.Customer;
import src.entity.Product;
import src.entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DashboardUI extends JFrame {

    private JPanel container;
    private JPanel panelTop;
    private JLabel lbl_top;
    private JButton EXITButton;
    private JTabbedPane pane_menu;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JComboBox comboBoxCustomerType;
    private JButton searchButton;
    private JButton clearButton;
    private JButton addNewButton;
    private JLabel lbl_fld_customer_name;
    private JLabel lbl_customer_type;
    private JTextField fld_customer_name_text;
    private JPanel pnl_product;
    private JPanel pnl_customer;
    private JScrollPane scrl_product;
    private JTable tbl_product;
    private JPanel pnl_filter_product;
    private JTextField fld_product_name;
    private JTextField fld_product_code;
    private JComboBox<Item > cmb_product_stock;
    private JButton btn_search_product;
    private JButton btn_clear_product;
    private JLabel lbl_product_name;
    private JLabel lbl_product_code;
    private JLabel lbl_product_stock_status;
    private JButton btn_add_product;
    private JPanel pnl_order;
    private JPanel pnl_basket_top;
    private JScrollPane scrl_basket;
    private JComboBox<Item> cmb_basket_customer;
    private JLabel lbl_combo_casket;
    private JLabel lbl_amount_total;
    private JLabel lbl_amount_total_enter;
    private JLabel lbl_product_amount;
    private JLabel lbl_product_amount_enter;
    private JButton btn_basket_clear;
    private JButton btn_basket_order;
    private JTable tbl_basket;
    private User loggedUser = null;
    private CustomerController customerController;
    private ProductController productController;
    private BasketController basketController;
    private DefaultTableModel tbmdl_customer = new DefaultTableModel();
    private DefaultTableModel tbmdl_product = new DefaultTableModel();
    private DefaultTableModel tbmdl_basket = new DefaultTableModel();
    private JPopupMenu popup_customer = new JPopupMenu();
    private JPopupMenu popup_product = new JPopupMenu();

    public DashboardUI(User user) {

        this.loggedUser = user;
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        this.basketController = new BasketController();

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

        //customer tab
        loadCustomerTable(customers);
        loadCustomerPopUpMenu();
        loadCustomerButtonEvent();

        this.comboBoxCustomerType.setModel(new DefaultComboBoxModel<>(Customer.CUSTYPE.values()));
        this.comboBoxCustomerType.setSelectedItem(null) ;

        //product tab
        loadProductTable(null);
        loadProductPopUpMenu();
        loadProductButtonEvent();
        this.cmb_product_stock.addItem(new Item(1, "In Stock"));
        this.cmb_product_stock.addItem(new Item(2, "Not In Stock"));
        this.cmb_product_stock.setSelectedItem(null); //default selected one this is for

        //basket tab
        loadBasketTable();
        loadBasketButtonEvent();
        loadBasketCustomerCombo();
    }

    private void loadBasketCustomerCombo() {
        ArrayList<Customer> customers = this.customerController.findAll();
        this.cmb_basket_customer.removeAllItems();

        for(Customer customer : customers) {
            int comboKey = customer.getId();
            String comboValue = customer.getName();
            this.cmb_basket_customer.addItem(new Item(comboKey, comboValue ));
        }
        this.cmb_basket_customer.setSelectedItem(null);
    }

    private void loadBasketButtonEvent(){
        this.btn_basket_clear.addActionListener(e -> {
            if(this.basketController.clear()){
                Helper.showMessage("done");
                loadBasketTable();
            } else {
                Helper.showMessage("error");
            }
        });
    }

    private void loadBasketTable() {
        Object[] columnproduct =   {"ID","Product Name", "Product Code ", "Price", "Stock"  };
        ArrayList<Basket> baskets = this.basketController.getAll();

        DefaultTableModel clearModel = (DefaultTableModel) tbl_basket.getModel();
        clearModel.setRowCount(0);

        this.tbmdl_basket.setColumnIdentifiers(columnproduct);
        int totalPrice = 0;

        for(Basket product : baskets) {
            Object[] rowproduct = {
                    product.getId(),
                    product.getProduct().getName(),
                    product.getProduct().getCode(),
                    product.getProduct().getPrice(),
                    product.getProduct().getStock()
            };
            this.tbmdl_basket.addRow(rowproduct);

            totalPrice += product.getProduct().getPrice();
        }
        this.lbl_amount_total_enter.setText(totalPrice + " euros ");
        this.lbl_product_amount_enter.setText(baskets.size()  + " many ");


        this.tbl_basket.setModel(tbmdl_basket);
        this.tbl_basket.getTableHeader().setReorderingAllowed(false);
        this.tbl_basket.getColumnModel().getColumn(0).setMaxWidth(30);
        this.tbl_basket.setEnabled(false);

    }

    private void loadProductButtonEvent() {
        this.btn_add_product.addActionListener(e -> {
            ProductUI productUI = new ProductUI(new Product());
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });
        });

        this.btn_search_product.addActionListener(e -> {
            ArrayList<Product> filteredProducts = this.productController.filterProduct(
                    this.fld_customer_name_text.getText(),
                    this.fld_product_code.getText(),
                    (Item)this.cmb_product_stock.getSelectedItem()
            );
            loadProductTable(filteredProducts);
        });

        this.btn_clear_product.addActionListener(e -> {
             this.fld_product_code.setText(null);
             this.fld_product_name.setText(null);
             this.cmb_product_stock.setSelectedItem(null);
             loadProductTable(null);
        });
    }

    private void loadProductPopUpMenu(){

        this.tbl_product.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_product.rowAtPoint(e.getPoint());
                tbl_product.setRowSelectionInterval(selectedRow,selectedRow); // to select the lines şn the table
            }
        });

        this.popup_product.add("Add to the Basket").addActionListener(e -> {
            int selectId = Integer.parseInt(this.tbl_product.getValueAt(tbl_product.getSelectedRow(), 0).toString());
            Product basketProduct = this.productController.findProductById(selectId);

            if(basketProduct.getStock() <=0){
                Helper.showMessage("This product has no stock");
            } else {
                Basket basket = new Basket(selectId);
                if(this.basketController.save(basket)){
                    Helper.showMessage("done");
                    loadBasketTable();
                } else {
                      Helper.showMessage("error");
                }
            }
        });
        this.popup_product.add("Update").addActionListener( e -> {
            int selectId = Integer.parseInt(this.tbl_product.getValueAt(tbl_product.getSelectedRow(), 0).toString());
            ProductUI productUI = new ProductUI(this.productController.findProductById(selectId));
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                    loadBasketTable();
                }
            });
        });
        this.popup_product.add("Delete").addActionListener(e -> {
            int selectId = Integer.parseInt(this.tbl_product.getValueAt(tbl_product.getSelectedRow(), 0).toString());
            if(Helper.confirm("sure")){
                if(this.productController.delete(selectId)){
                    Helper.showMessage("done");
                    loadProductTable(null);
                    loadBasketTable();
                } else {
                    Helper.showMessage("error");
                }
            }

        });

        this.tbl_product.setComponentPopupMenu(this.popup_product);

    }

    private void loadProductTable(ArrayList<Product> products) {
        Object[] columnproduct =   {"ID","Product Name", "Product Code ", "Price", "Stock"  };

        if(products == null  || products.isEmpty()) {
            products = this.productController.findAll();
        }
        
        DefaultTableModel clearModel = (DefaultTableModel) tbl_product.getModel();
        clearModel.setRowCount(0);

        this.tbmdl_product.setColumnIdentifiers(columnproduct);

        for(Product product : products) {
            Object[] rowproduct = {
                    product.getId(),
                    product.getName(),
                    product.getCode(),
                    product.getPrice(),
                    product.getStock()
            };
            this.tbmdl_product.addRow(rowproduct);
        }

        this.tbl_product.setModel(tbmdl_product);
        this.tbl_product.getTableHeader().setReorderingAllowed(false);
        this.tbl_product.getColumnModel().getColumn(0).setMaxWidth(30);
        this.tbl_product.setEnabled(false);
    }

    private void loadCustomerButtonEvent()  {
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
                    loadBasketCustomerCombo();
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
                    loadBasketCustomerCombo();
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

//stock column in product doesn't exist   --> needs fixing
