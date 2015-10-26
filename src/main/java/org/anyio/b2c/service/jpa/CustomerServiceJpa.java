package org.anyio.b2c.service.jpa;

import org.anyio.b2c.domain.Customer;
import org.anyio.b2c.repository.CustomerRepository;
import org.anyio.b2c.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CustomerService with SpringDataJPA implementation.
 * 
 * @author promise
 *
 */
@Service
public class CustomerServiceJpa implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Iterable<Customer> listAllCustomer() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomerById(Long id) {
		return customerRepository.findOne(id);
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(Long id) {
		customerRepository.delete(id);
	}

}
