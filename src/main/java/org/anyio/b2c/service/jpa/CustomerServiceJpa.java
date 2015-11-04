package org.anyio.b2c.service.jpa;

import org.anyio.b2c.domain.Customer;
import org.anyio.b2c.repository.CustomerRepository;
import org.anyio.b2c.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

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

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping
	public Page<Customer> listCustomers(Pageable pageable) {
		return customerRepository.findAll(pageable);
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
