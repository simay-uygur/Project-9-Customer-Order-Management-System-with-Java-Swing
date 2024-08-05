package src.business;

import src.core.Helper;
import src.dao.ProductDao;
import src.entity.Product;

import java.util.ArrayList;

public class ProductController {
    private final ProductDao productDao = new ProductDao();

    public ArrayList<Product> findAll() {
        return this.productDao.findAll();
    }

    public boolean save (Product product) {
        return this.productDao.save(product);
    }

    public boolean update(Product product) {
        if(this.getById(product.getId()) == null) {
            Helper.showMessage(product.getId() + "the product with this id does not exist");
            return false;
        }
        return this.productDao.update(product);
    }

    public boolean delete(int id) {
        if(this.getById(id) == null)
        {
            Helper.showMessage("Product not found with id " + id);
            return false;
        }
        return this.productDao.delete(id); //not sure
    }

    public Product getById(int id) {
        return this.productDao.getById(id);
    }
}
