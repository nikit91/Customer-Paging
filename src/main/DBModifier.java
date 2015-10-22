/**
 * Run this main class to generate dummy records into customer table
 */

package main;

import java.util.Set;

import misc.Util;
import bean.Customer;
import dao.GenericDAO;

public class DBModifier {
	public static GenericDAO genDAO;
	static {
		genDAO = new GenericDAO();
	}

	public static void main(String[] args) {
		System.out.println("Starting Application.");
		//Number of records to be generated
		Integer numberOfRecords = 100;
		Set<Customer> cSet = Util.generateRecords(numberOfRecords);
		try {
			genDAO.addSet(cSet);
			System.out.println(cSet.size() + " Records added successfully.");
		} catch (Exception e) {
		}
	}

}
