package src;
import java.sql.Connection;

import src.business.UserController;
import src.core.Database;
import src.core.Helper;
import src.entity.User;
import src.view.DashboardUI;
import src.view.LoginUI;

public class App {
    

    public static void main(String[] args) {
        Helper.setTheme();
        //LoginUI loginUI = new LoginUI();
        Connection connection = Database.getInstance();

        UserController userController = new UserController();
        User user = userController.findByLogIn("simayuygur09@gmail.com", "1212");
        DashboardUI dashboard = new DashboardUI(user);
    }

    
}
