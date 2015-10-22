package controller;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import service.CustomerService;
import bean.Customer;
import exception.DAOException;

@RemoteProxy(name="RequestReceiver")
public class RequestReceiver {
	public static CustomerService custService;
	static{
		custService = new CustomerService();
	}
	/**
	 * function to get all the customer records from db
	 * @return
	 */
	@RemoteMethod
	public List<Customer> getCustomers(){
		List<Customer> cList=null;
		try {
			cList = custService.getAllCustomers();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return cList;
	}
	/**
	 * function to get paged customer records
	 * @param from - page offset
	 * @param pageSize - page size
	 * @return List of Customer records
	 */
	@RemoteMethod
	public List<Customer> getPagedCustomerData(int from,int pageSize){
		List<Customer> cList=null;
		try {
			cList = custService.getPagedCutomerData(from, pageSize);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return cList;
	}
	/**
	 * Method to number of customer records present in DB
	 * @return
	 */
	@RemoteMethod
	public Long getCustCount(){
		Long custCount=null;
		try {
			custCount =  custService.getCustomerCount();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return custCount;
	}
}

