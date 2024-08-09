package src.dao;

import src.business.ProductController;
import src.core.Database;
import src.entity.Cart;
import src.entity.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CartDao {

    Connection con;
    private ProductDao productDao;
    private CustomerDao customerDao;

    public CartDao(){
        this.con = Database.getInstance();
        this.productDao = new ProductDao();
        this.customerDao = new CustomerDao();

    }

    public ArrayList<Cart> findAll()
    {
        ArrayList<Cart> carts = new ArrayList<>();
        String query = "SELECT * FROM cart";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while(rs.next())
            {
                Cart cus = this.match(rs);
                carts.add(cus);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carts;
    }

    private Cart match(ResultSet rs) throws SQLException {
        Cart cart  = new Cart();

        cart.setId(rs.getInt("id"));
        cart.setPrice(rs.getInt("price"));
        cart.setCustomerId(rs.getInt("customerId"));
        cart.setProductId(rs.getInt("productId"));
        cart.setNote(rs.getString("note"));
        cart.setDate(LocalDate.parse(rs.getString("date")));
        cart.setCustomer(this.customerDao.getById(cart.getCustomerId()));
        cart.setProduct(this.productDao.getById(cart.getProductId()));

        return cart;
    }

    public boolean save(Cart cart) {
        String query = "INSERT INTO cart (" +
                "customerId," +
                "productId," +
                "price," +
                "date," +
                "note) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, cart.getCustomerId());
            pr.setInt(2, cart.getProductId());
            pr.setInt(3, cart.getPrice());
            pr.setDate(4, Date.valueOf(cart.getDate()));
            pr.setString(5, cart.getNote());

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
