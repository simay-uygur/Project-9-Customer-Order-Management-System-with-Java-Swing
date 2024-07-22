package src.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import src.core.Database;
import src.entity.User;


public class UserDao {
    
     private Connection con;

    public UserDao() {
        this.con = Database.getInstance();

    }

    public User findByLogin(String mail, String password)
    {
        User user = null;
        String query =  "SELECT * FROM user WHERE mail = ? AND password = ?"; //jdbc connectionda replace edeceğiz
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1,mail);
            pr.setString(2,password);
            ResultSet rs = pr.executeQuery(); // bir sonuç kümesi oluştur
            if(rs.next()){
                user = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public ArrayList<User> findAll()
    {

        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while(rs.next())
            {
                User user = this.match(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public User match(ResultSet rs) throws SQLException {
        User user = new User();

        user.setMail(rs.getString("mail"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setId(rs.getInt("id"));

        return user;
    }

}
