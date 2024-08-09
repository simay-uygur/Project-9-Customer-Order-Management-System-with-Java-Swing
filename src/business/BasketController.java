package src.business;

import src.core.Helper;
import src.dao.BasketDao;
import src.entity.Basket;
import java.util.ArrayList;

public class BasketController {
    private final BasketDao basketDao = new BasketDao();

    public boolean save(Basket basket){ //i first thought using product object as a parameter but
        return this.basketDao.save(basket);
    }

    public ArrayList<Basket> getAll(){
        return basketDao.findAll();
    }

    public boolean clear(){
        return this.basketDao.clear();
    }

}
