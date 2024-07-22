package src;
import java.sql.Connection;
import src.core.Database;
import src.core.Helper;
import src.view.LoginUI;

public class App {
    

    public static void main(String[] args) {
        Helper.setTheme();
        LoginUI loginUI = new LoginUI();
        Connection connection1 = Database.getInstance();
        Connection connection2 = Database.getInstance(); 
            
    }

    
}
