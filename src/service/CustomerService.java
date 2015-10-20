package service;

import java.util.List;

import bean.Customer;
import dao.GenericDAO;
import exception.DAOException;

public class CustomerService {
	
	//Fetch customer data functions
	public List<Customer> getAllCustomers() throws DAOException{
		StringBuilder hql = new StringBuilder("from Customer");
		GenericDAO genDAO = new GenericDAO();
		List<Customer> cList = (List<Customer>) genDAO.findByQuery(hql, null);
		return cList;
	}

}
