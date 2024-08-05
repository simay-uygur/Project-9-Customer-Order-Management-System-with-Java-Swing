package src.business;

import src.core.Helper;
import src.core.Item;
import src.dao.ProductDao;
import src.entity.Customer;
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
        if(this.findProductById(product.getId()) == null) {
            Helper.showMessage(product.getId() + "the product with this id does not exist");
            return false;
        }
        return this.productDao.update(product);
    }

    public boolean delete(int id) {
        if(this.findProductById(id) == null)
        {
            Helper.showMessage("Product not found with id " + id);
            return false;
        }
        return this.productDao.delete(id); //not sure
    }

    public Product findProductById(int id) {
        return this.productDao.getById(id);
    }


    public  ArrayList<Product> filterProduct(String name, String code, Item isStock){
        String query = "SELECT * FROM product";

        ArrayList<String> whereList = new ArrayList<>();

        if(name.length() > 0){
            whereList.add("name LIKE '%"+name+"%'");
        }

        if(code.length() > 0){
            whereList.add("code LIKE '%"+code+"%'");
        }

        if(isStock != null){
            if(isStock.getKey() ==1){
                whereList.add("isStock > 0");
            } else {
                whereList.add("isStock <= 0");
            }
        }

        if(!whereList.isEmpty()){
            query += " WHERE " + String.join(" AND ", whereList);
        }

        return this.productDao.query(query);
    }

}
