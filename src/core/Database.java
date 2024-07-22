package src.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//design pattern  
/**
 * Database
 */
public class Database {

    private static Database instance = null;  
    private Connection connection = null;
    private final String DB_URL =  "jdbc:mysql://localhost:3307/veritabani";
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    //singleton design pattern 
    private Database() {

        try {
            System.out.println("Connecting to database...");

            this.connection = DriverManager.getConnection( DB_URL, USERNAME, PASSWORD);
            
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
    
    private Connection getConnection() {
        return this.connection;
    }

    public static Connection getInstance(){
        try {
            if(instance == null || instance.getConnection().isClosed())
            {
                instance = new Database(); 
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return instance.getConnection();
    }
    
}