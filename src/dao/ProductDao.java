package src.dao;

import src.core.Database;
import src.entity.Customer;
import src.entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDao {

    Connection con;

    public ProductDao(){
        this.con = Database.getInstance();
    }

    public ArrayList<Product> findAll()
    {

        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while(rs.next())
            {
                Product cus = this.match(rs);
                products.add(cus);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    private Product match(ResultSet rs) throws SQLException {
        Product pro = new Product();

        pro.setId(rs.getInt("id"));
        pro.setCode(rs.getString("code"));
        pro.setName(rs.getString("name"));
        pro.setPrice(rs.getInt("price"));
        pro.setStock(rs.getInt("stock"));

        return pro;
    }

    public boolean save(Product product) {
        String query = "INSERT INTO product (name,code,price,stock) VALUES(?,?,?,?)";

        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, product.getName());
            pr.setString(2, product.getCode());
            pr.setInt(3, product.getPrice());
            pr.setInt(4, product.getStock());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean update(Product product) {
        String query = "UPDATE product SET " +
                "name = ? ," +
                "code = ? ," +
                "price = ? ," +
                "stock = ? " +
                "WHERE id = ? " ;
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, product.getName());
            pr.setString(2, product.getCode());
            pr.setInt(3, product.getPrice());
            pr.setInt(4, product.getStock());
            pr.setInt(5, product.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean delete(int id) {
        String query = "DELETE FROM product WHERE id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Product getById(int id) {
        Product object = null;
        String query = "SELECT * FROM product WHERE id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                object = this.match(rs);
            }
        } catch (SQLException e) {

        }
        return object;

    }

    public ArrayList<Product> query(String query) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                products.add(this.match(rs));
            }
        } catch (SQLException e) {

        }
        return products;
    }

}
