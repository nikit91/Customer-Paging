package main;

import java.util.Set;

import misc.Util;
import bean.Customer;
import dao.GenericDAO;

public class DBModifier {

	public static void main(String[] args) {
		System.out.println("Starting Application.");
		GenericDAO genDAO = new GenericDAO();
		Set<Customer> cSet = Util.generateRecords(1000);
		try {
			genDAO.addSet(cSet);
			System.out.println(cSet.size()+" Records added successfully.");
		} catch (Exception e) {
		}
	}

}
