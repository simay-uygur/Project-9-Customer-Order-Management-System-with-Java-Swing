package src.dao;

import src.core.Database;
import src.entity.Customer;
import src.entity.User;

import java.sql.Connection;
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
