package src.dao;

import src.core.Database;
import src.entity.Basket;
import src.entity.Customer;
import src.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BasketDao {

    Connection con;
    private ProductDao productDao = new ProductDao();

    public BasketDao(){
        this.con = Database.getInstance();
    }
// fix it
    public boolean save(Basket basket) {
        Product pro = this.productDao.getById(basket.getProductId());
        if(pro == null || pro.getStock() <= 0){
            return false; //not sure about this
        }
        String query = "INSERT INTO basket (productId) VALUES(?)";

        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, pro.getId()  );

            return pr.executeUpdate() != -1; // if i dont give a default value in sql it gives exception.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Basket match(ResultSet rs) throws SQLException {
        Basket cus = new Basket();

        cus.setId(rs.getInt("id"));
        cus.setProductId(rs.getInt("productId"));
        cus.setProduct(this.productDao.getById(rs.getInt("productId")));
        return cus;
    }

    public Product getById(int id) {
        return this.productDao.getById(id);
    }

    public ArrayList<Basket> findAll()
    {

        ArrayList<Basket> baskets = new ArrayList<>();
        String query = "SELECT * FROM basket";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while(rs.next())
            {
                 Basket bas = this.match(rs);
                baskets.add(bas);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return baskets;
    }

    public boolean clear() {  // may have some issues
        String query = "DELETE FROM basket";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
