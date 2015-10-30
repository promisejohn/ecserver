package org.anyio.b2c.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.anyio.b2c.EcserverApplication;
import org.anyio.b2c.config.PersistConfig;
import org.anyio.b2c.domain.Customer;
import org.anyio.b2c.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
			Customer c = new Customer();
			c.setName("test" + i);
			customerService.saveCustomer(c);
		}
	}

	@Test
	public void testListAllCustomer() {
		Iterator<Customer> iter = customerService.listAllCustomer().iterator();
		int s = 0;
		while (iter.hasNext()) {
			iter.next();
			++s;
		}
		assertEquals(3, s);
	}

	@Test
	public void testGetCustomerById() {
		Customer c = new Customer();
		c.setName("testGetCustomerById");
		customerService.saveCustomer(c);
		Customer cc = customerService.getCustomerById(c.getId());
		assertEquals(c.getId(), cc.getId());
	}

	@Test
	public void testSaveCustomer() {
		Customer c = new Customer();
		c.setName("testSaveCustomer");
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
}