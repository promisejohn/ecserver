package org.anyio.b2c.service.jpa;

import org.anyio.b2c.domain.jpa.QCustomer;
import org.anyio.b2c.repository.jpa.CustomerRepository;
import org.anyio.b2c.domain.jpa.Customer;
import org.anyio.b2c.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysema.query.types.Predicate;

/**
 * CustomerService with SpringDataJPA implementation.
 * 
 * More on:
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/
 * cache.html
 * 
 * Complex situation consider using AOP.
 * 
 * @author promise
 *
 */
@Service
// @CacheConfig("customercache")
public class CustomerServiceJpa implements CustomerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceJpa.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	@Cacheable("customercache")
	public Iterable<Customer> listAllCustomer() {
		log.warn("Cache Miss! List all customers from database.");
		return customerRepository.findAll();
	}

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	@Override
	// @Cacheable(value = "customercache", keyGenerator = "keyGenerator")
	// PageImpl not serializable, wrapping with a standard bean will work, see:
	// http://stackoverflow.com/questions/14445515/jsonmappingexception-cannot-desierialize-java-object
	public Page<Customer> listCustomers(Pageable pageable) {
		log.warn("Cache Miss! List all customers by page from database.");
		return customerRepository.findAll(pageable);
	}

	@Override
	@Cacheable(value = "singleCustomer")
	public Customer getCustomerById(Long id) {
		log.warn("Cache Miss! show customer from database.");
		return customerRepository.findOne(id);
	}

	@Override
	// @Cacheable("customercache")
	public Page<Customer> searchCustomers(String searchTerm, Pageable pageable) {
		log.warn("Cache Miss! search customers by page with keyword: {} from database.", searchTerm);
		QCustomer customer = QCustomer.customer;
		Predicate pred = customer.name.containsIgnoreCase(searchTerm).or(customer.email.containsIgnoreCase(searchTerm));
		return customerRepository.findAll(pred, pageable);
	}

	@Override
	@Cacheable("customercache")
	public Iterable<Customer> searchCustomers(String searchTerm) {
		log.warn("Cache Miss! search all customers with keyword: {} from database.", searchTerm);
		QCustomer customer = QCustomer.customer;
		Predicate pred = customer.name.containsIgnoreCase(searchTerm).or(customer.email.containsIgnoreCase(searchTerm));
		return customerRepository.findAll(pred);
	}

	@Override
	@CacheEvict(value = { "customercache", "singleCustomer" }, allEntries = true)
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	@CacheEvict(value = { "customercache" }, allEntries = true)
	// @Caching(evict = { @CacheEvict("customercache"), @CacheEvict(value =
	// "singleCustomer", key = "") })
	public void deleteCustomer(Long id) {
		customerRepository.delete(id);
	}

}
