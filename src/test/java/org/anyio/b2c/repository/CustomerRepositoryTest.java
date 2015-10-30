package org.anyio.b2c.repository;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.anyio.b2c.config.PersistConfig;
import org.anyio.b2c.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { PersistConfig.class })
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Before
	public void setUp() {
		customerRepository.deleteAll();
		for (int i = 0; i < 3; i++) {
			Customer c = new Customer();
			c.setName("test" + i);
			customerRepository.save(c);
		}
	}

	@Test
	public void testSaveCustomer() {
		Customer c = new Customer();
		c.setName("testSave");
		assertNull(c.getId());
		customerRepository.save(c);
		assertNotNull(c.getId());

		Customer cfetch = customerRepository.findOne(c.getId());
		assertNotNull(cfetch);
		assertEquals(c.getId(), cfetch.getId());
	}

	@Test
	public void testListAllCustomers() {
		int s = 0;
		Iterator<Customer> iter = customerRepository.findAll().iterator();
		while (iter.hasNext()) {
			++s;
			iter.next();
		}
		assertEquals(3, s);
	}

	@Test
	public void testFindOneCustomer() {
		Customer c = customerRepository.findByName("test1").get(0);
		Customer cc = customerRepository.findOne(c.getId());
		assertEquals(cc.getId(), c.getId());
	}

	@Test
	public void testDeleteCustomer() {
		Customer c = customerRepository.findByName("test1").get(0);
		Long id = c.getId();
		customerRepository.delete(c);
		c = customerRepository.findOne(id);
		assertNull(c);
	}

}
