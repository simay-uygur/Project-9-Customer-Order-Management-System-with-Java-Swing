package src.business;

import src.dao.CartDao;
import src.entity.Cart;

import java.util.ArrayList;

public class CartController {

    private final CartDao cartDao = new CartDao();

    public boolean savw(Cart cart) {
        return this.cartDao.save(cart);
    }

    public ArrayList<Cart> findAll() {
        return this.cartDao.findAll();
    }
}
