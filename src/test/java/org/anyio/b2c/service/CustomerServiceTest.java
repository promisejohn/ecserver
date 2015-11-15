package org.anyio.b2c.service;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Iterator;

import org.anyio.b2c.EcserverApplication;
import org.anyio.b2c.domain.jpa.Customer;
import org.anyio.b2c.repository.jpa.CustomerRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { EcserverApplication.class })
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@Before
	public void setUp() {
		customerRepository.deleteAll();
		for (int i = 0; i < 3; i++) {
			Customer c = new Customer("test" + i, "test" + i + "@anyio.org", "test" + i + "mobile");
			customerService.saveCustomer(c);
		}
	}

	@Test
	public void testListAllCustomer() {
		assertThat(IteratorUtils.toList(customerService.listAllCustomer().iterator()).size(), equalTo(3));
	}

	@Test
	public void testListAllCustomerPage() {
		Page<Customer> page = customerService.listCustomers(new PageRequest(100, 10));
		assertEquals(1, page.getTotalPages());
	}

	@Test
	public void testGetCustomerById() {
		Customer c = new Customer("testGetCustomerById", "testGetCustomerById@anyio.org", "testGetCustomerByIdMobile");
		customerService.saveCustomer(c);
		Customer cc = customerService.getCustomerById(c.getId());
		assertEquals(c.getId(), cc.getId());
	}

	@Test
	public void testSaveCustomer() {
		Customer c = new Customer("testSaveCustomer", "testSaveCustomer@anyio.org", "testSaveCustomerMobile");
		assertNull(c.getId());
		customerService.saveCustomer(c);
		assertNotNull(c.getId());
	}

	@Test
	public void testDeleteCustomer() {
		Iterator<Customer> iter = customerService.listAllCustomer().iterator();
		Customer c = iter.next();
		customerService.deleteCustomer(c.getId());
		c = customerService.getCustomerById(c.getId());
		assertNull(c);
	}

	@Test
	public void testSearchCustomers() {
		Page<Customer> page = customerService.searchCustomers("test1", new PageRequest(1, 1));
		assertThat(page.getTotalElements(), equalTo(1L));
		page = customerService.searchCustomers("test", new PageRequest(1, 1));
		assertThat(page.getTotalElements(), equalTo(3L));
	}
}
