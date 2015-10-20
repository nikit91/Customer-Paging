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
	@RemoteMethod
	public String demoFunc(){
		return "Success Bitch!";
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
			cList = new CustomerService().getAllCustomers();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return cList;
	}
}

