import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

public class App {
    

    public static void main(String[] args) {
        

        try {
            System.out.println("Connecting to database...");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/customerservice", "admin", "admin");
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
            
    }
        
    
}
