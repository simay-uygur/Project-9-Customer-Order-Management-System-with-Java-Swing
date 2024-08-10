package src.view;

import src.business.BasketController;
import src.business.CartController;
import src.business.ProductController;
import src.core.Helper;
import src.entity.Basket;
import src.entity.Cart;
import src.entity.Customer;
import src.entity.Product;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CartUI extends JFrame{
    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_customer_name;
    private JTextField fld_cart_date;
    private JTextArea txtarea_cart_note;
    private JButton btn_cart;
    private JLabel lbl_cart_date;
    private JLabel lbl_cart_note;
    private Customer customer;
    private BasketController basketController;
    private CartController cartController;
    private ProductController productController;

    private DashboardUI dashboardUI;

    public CartUI(Customer customer, DashboardUI dashboardUI){
        this.customer = customer;
        this.basketController = new BasketController();
        this.cartController = new CartController();
        this.productController = new ProductController();

        this.dashboardUI = dashboardUI;

        this.add(container);
        this.setTitle("MAKE AN ORDER");
        this.setSize(700,500);

        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2;

        this.setLocation(x,y);

        if(customer.getId() == 0){
            Helper.showMessage("Please select a valid customer!!! ");
            dispose();
        }

        ArrayList<Basket> baskets = this.basketController.getAll();
        if(baskets.size() == 0){
            Helper.showMessage("Please add product to the basket!");
            dispose();
        }

        this.lbl_customer_name.setText("Customer name: " + this.customer.getName());

        this.btn_cart.addActionListener(e -> {
            if(this.fld_cart_date.getText().isEmpty()){
                Helper.showMessage("fill");
            } else {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (Basket basket : baskets) {
                    if(basket.getProduct().getStock() <= 0 ) continue; //if some product is out of stock after adding to cart but should not be added and allowed to be bought.
                    Cart cart = new Cart();
                    cart.setCustomerId(this.customer.getId());
                    cart.setProductId(basket.getProductId());
                    cart.setPrice(basket.getProduct().getPrice());
                    cart.setDate(LocalDate.parse(this.fld_cart_date.getText(), timeFormatter));
                    cart.setNote(this.txtarea_cart_note.getText());

                    this.cartController.save(cart);

                    Product unstockedProduct = basket.getProduct();
                    unstockedProduct.setStock(unstockedProduct.getStock() -1 );
                    this.productController.update(unstockedProduct);

                }
                this.basketController.clear();
                Helper.showMessage("done");
                this.dashboardUI.loadCart();
                dispose();
            }
        });
    }

    private void createUIComponents() throws ParseException {
        this.fld_cart_date = new JFormattedTextField( new MaskFormatter("##/##/####"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fld_cart_date.setText(formatter.format(LocalDate.now() ));
    }
}
