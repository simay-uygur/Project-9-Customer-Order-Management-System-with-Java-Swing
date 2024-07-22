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

    //git commit --amend -m "ynei bi mesaj"
    //git log : eski commmitleri görebiliyoruz 
    //git revert commitId (en az yedi basamak)
    //git log -n 2 (2 tane getir)    
    //git reset --hard commitId (en az yedi basamak) bu önerilmiyo çok direkt geri dönüyor
    //git diff commitid.. commitid bakmakistediğimdosya
    // git stash pop  : en son yaptığımız değişiklikleri geri alır
    //git checkout otherbranch
    //git branch 
    //git merge
    //git --squash
    //git merge --abort 
    //rebase de merge giib 
    
}
