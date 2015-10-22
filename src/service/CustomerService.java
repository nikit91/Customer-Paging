package service;

import java.util.List;

import bean.Customer;
import dao.GenericDAO;
import exception.DAOException;

public class CustomerService {
	public static GenericDAO genDAO ;
	/**
	 * Static block to initiliaze GenericDAO instance for service
	 */
	static {
		genDAO = new GenericDAO();
	}
	/**
	 * Method to retrieve Number of Customer records in db
	 * @return number of customer records present
	 * @throws DAOException - Exception if any
	 */
	public Long getCustomerCount() throws DAOException{
		StringBuilder hql = new StringBuilder("SELECT COUNT(*) FROM Customer");
		Long count = ((List<Long>) genDAO.findByQuery(hql, null)).get(0);
		return count;
	}
	/**
	 * Method to retrieve all Customer records from db
	 * @return
	 * @throws DAOException
	 */
	public List<Customer> getAllCustomers() throws DAOException{
		StringBuilder hql = new StringBuilder("FROM Customer");
		List<Customer> cList = (List<Customer>) genDAO.findByQuery(hql, null);
		return cList;
	}
	/**
	 * Method to retrieve paged Customer records from db
	 * @param from
	 * @param pageSize
	 * @return
	 * @throws DAOException
	 */
	public List<Customer> getPagedCutomerData(int from, int pageSize) throws DAOException{
		StringBuilder hql = new StringBuilder("FROM Customer ORDER BY customerid");
		List<Customer> cList = (List<Customer>) genDAO.findByQueryPaged(hql, null,from,pageSize);
		return cList;
	}

}
