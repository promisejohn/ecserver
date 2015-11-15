package org.anyio.b2c.service;

import org.anyio.b2c.domain.jpa.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

	Iterable<Customer> listAllCustomer();

	Customer getCustomerById(Long id);

	Customer saveCustomer(Customer customer);

	void deleteCustomer(Long id);

	Page<Customer> listCustomers(Pageable pageable);

	Page<Customer> searchCustomers(String searchTerm, Pageable pageable);

	Iterable<Customer> searchCustomers(String searchTerm);
}
