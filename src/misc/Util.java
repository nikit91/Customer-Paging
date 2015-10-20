package misc;

import java.util.HashSet;
import java.util.Set;

import bean.Customer;

public class Util {

	public static Set<Customer> generateRecords(int numberOfRecords) {
		Set<Customer> cSet = new HashSet<Customer>();
		Customer customer = null;
		for (int i = 1; i <= numberOfRecords; i++) {
			customer = new Customer();
			cSet.add(customer);
		}
		return cSet;
	}
}
