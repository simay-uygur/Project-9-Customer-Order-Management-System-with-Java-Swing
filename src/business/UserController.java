package src.business;

import src.core.Helper;
import src.dao.UserDao;
import src.entity.User;

public class UserController {

      private final UserDao userDao = new UserDao();

      public User findByLogIn(String mail, String password)
      {
          if(!Helper.isEmailValid(mail)) {
              return null;
          }
          return this.userDao.findByLogin(mail,password);
      }
}
