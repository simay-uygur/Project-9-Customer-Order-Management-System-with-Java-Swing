package src.business;

import src.core.Helper;
import src.dao.CustomerDao;
import src.entity.Customer;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDao customerDao = new CustomerDao();

    public ArrayList<Customer> findAll()
    {
        return this.customerDao.findAll();
    }

    public boolean save(Customer customer){
        return this.customerDao.save(customer);
    }

    public Customer findByIdCustomer(int id){
        return this.customerDao.getById(id);
    }

    public boolean update(Customer customer){
        if(this.findByIdCustomer(customer.getId()) == null){
            Helper.showMessage("Customer not found with id " + customer.getId());
            return false;
        }

        return this.customerDao.update(customer);
    }

    public boolean delete(int id){
        if(this.findByIdCustomer(id) == null)
        {
            Helper.showMessage("Customer not found with id " + id);
            return false;
        }
        return this.customerDao.delete(id);
    }

    public  ArrayList<Customer> filterCustomer(String name, Customer.CUSTYPE type){
         String query = "SELECT * FROM customer";

         ArrayList<String> whereList = new ArrayList<>();

         if(name.length() > 0){
             whereList.add("name LIKE '%"+name+"%'");
         }

         if(type != null){
             whereList.add("type LIKE '%"+type+"%'");
         }

         if(!whereList.isEmpty()){
             query += " WHERE " + String.join(" AND ", whereList);
         }

         return this.customerDao.query(query);
    }

}
