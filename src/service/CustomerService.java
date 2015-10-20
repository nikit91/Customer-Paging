package service;

import java.util.List;

import bean.Customer;
import dao.GenericDAO;
import exception.DAOException;

public class CustomerService {
	public static GenericDAO genDAO ;
	static {
		genDAO = new GenericDAO();
	}
	public Long getCustomerCount() throws DAOException{
		StringBuilder hql = new StringBuilder("SELECT COUNT(*) FROM Customer");
		Long count = ((List<Long>) genDAO.findByQuery(hql, null)).get(0);
		return count;
	}
	//Fetch customer data functions
	public List<Customer> getAllCustomers() throws DAOException{
		StringBuilder hql = new StringBuilder("FROM Customer");
		List<Customer> cList = (List<Customer>) genDAO.findByQuery(hql, null);
		return cList;
	}
	
	public List<Customer> getPagedCutomerData(int from, int pageSize) throws DAOException{
		StringBuilder hql = new StringBuilder("FROM Customer ORDER BY customerid");
		List<Customer> cList = (List<Customer>) genDAO.findByQueryPaged(hql, null,from,pageSize);
		return cList;
	}

}
