package main;

import java.util.List;
import java.util.Set;

import misc.Util;
import bean.Customer;
import dao.GenericDAO;
import exception.DAOException;

public class DBModifier {
	public static GenericDAO genDAO;
	static {
		genDAO = new GenericDAO();
	}
	public static void main(String[] args) {
		System.out.println("Starting Application.");
		
		Set<Customer> cSet = Util.generateRecords(100);
		try {
			List<Customer> cList = getAllCustomers();
			//genDAO.addSet(cSet);
			System.out.println(cSet.size()+" Records added successfully.");
		} catch (Exception e) {
		}
	}
	
	public static List<Customer> getAllCustomers() throws DAOException{
		StringBuilder hql = new StringBuilder("select count(*) from Customer order by customerid");
		List<Customer> cList = (List<Customer>) genDAO.findByQuery(hql, null);
		return cList;
	}
	
	

}
