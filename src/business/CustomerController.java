package src.business;

import src.dao.CustomerDao;
import src.entity.Customer;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDao customerDao = new CustomerDao();

    public ArrayList<  Customer> findAll()
    {
        return this.customerDao.findAll();
    }
}
