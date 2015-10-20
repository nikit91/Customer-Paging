package controller;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import exception.DAOException;
import service.CustomerService;
import bean.Customer;

@RemoteProxy(name="RequestReceiver")
public class RequestReceiver {
	public static CustomerService custService;
	static{
		custService = new CustomerService();
	}
	@RemoteMethod
	public List<String> getNames(){
		List<String> names= new ArrayList<String>();
		names.add("Nikit Srivastava");
		names.add("Alexandra Srivastava");
		return names;
	}
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

