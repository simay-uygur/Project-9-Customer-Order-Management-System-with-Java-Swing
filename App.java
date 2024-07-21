import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

public class App {
    

    public static void main(String[] args) {
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/veritabani","root", "root");
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
           y
            e.printStackTrace();
        }
    }
}
