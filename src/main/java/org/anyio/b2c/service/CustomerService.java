package org.anyio.b2c.service;

import org.anyio.b2c.domain.Customer;

public interface CustomerService {

	Iterable<Customer> listAllCustomer();

	Customer getCustomerById(Long id);

	Customer saveCustomer(Customer customer);

	void deleteCustomer(Long id);
}
