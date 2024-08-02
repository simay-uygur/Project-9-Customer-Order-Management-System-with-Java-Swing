package src.dao;

import src.core.Database;
import src.entity.Customer;
import src.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDao {

    Connection con;

    public CustomerDao(){
        this.con = Database.getInstance();
    }

    public ArrayList<Customer> findAll()
    {

        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while(rs.next())
            {
                Customer cus = this.match(rs);
                customers.add(cus);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customers;
    }

    public boolean save(Customer customer) {
        String query = "INSERT INTO customer (name,type,phone,mail,address) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, customer.getName());
            pr.setString(2, customer.getType().toString());
            pr.setString(3, customer.getPhone());
            pr.setString(4, customer.getMail());
            pr.setString(5, customer.getAddress());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }//reutnr true
    }

    public Customer getById(int id) {
        Customer object = null;
        String query = "SELECT * FROM customer WHERE id = ?";
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

    public boolean update(Customer customer) {
        String query = "UPDATE customer SET " +
                        "name = ? ," +
                        "type = ? ," +
                        "phone = ? ," +
                        "mail = ? ," +
                        "address = ? " +
                        "WHERE id = ? " ;
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, customer.getName());
            pr.setString(2, customer.getType().toString());
            pr.setString(4, customer.getMail());
            pr.setString(3, customer.getPhone());
            pr.setString(5, customer.getAddress());
            pr.setInt(6, customer.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM customer WHERE id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Customer match(ResultSet rs) throws SQLException {
        Customer cus = new Customer();

        cus.setMail(rs.getString("mail"));
        cus.setId(rs.getInt("id"));
        cus.setAddress(rs.getString("address"));
        cus.setPhone(rs.getString("phone"));
        cus.setType(Customer.CUSTYPE.valueOf(rs.getString("type"))); //valueof is logical
        cus.setName(rs.getString("name"));
        cus.setId(rs.getInt("id"));

        return cus;
    }


}
