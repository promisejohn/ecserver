package org.anyio.b2c.repository;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.anyio.b2c.config.PersistConfig;
import org.anyio.b2c.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
			Customer c = new Customer("test" + i, "test" + i + "@anyio.org", "test" + i + "mobile");
			customerRepository.save(c);
		}
	}

	@Test
	public void testSaveCustomer() {
		Customer c = new Customer("testSave", "testsave@anyio.org", "testsaveMobile");
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
	public void testListAllCustomersPage() {
		Page<Customer> page = customerRepository.findAll(new PageRequest(100,10));
		assertEquals(1, page.getTotalPages());
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

	@Test
	public void testFindByNameContainsOrEmailContainsAllIgnoreCase() {
		List<Customer> list = customerRepository.findByNameContainsOrEmailContainsAllIgnoreCase("test", "anyio",
				new PageRequest(0, 2));
		assertEquals(2, list.size());
	}

	@Test
	public void testFindBySearchTerm() {
		Page<Customer> page = customerRepository.findBySearchTermPage("test", new PageRequest(0, 2));
		assertEquals(2, page.getContent().size());
		List<Customer> list = customerRepository.findBySearchTerm("test", new PageRequest(0, 2));
		assertEquals(2, list.size());
		for (int i = 0; i < list.size(); i++) {
			assertEquals(list.get(i).getId(), page.getContent().get(i).getId());
		}
		Slice<Customer> slice = customerRepository.findBySearchTermSlice("test",  new PageRequest(0, 2));
		for (int i = 0; i < 2; i++) {
			assertEquals(list.get(i).getId(), slice.getContent().get(i).getId());
		}
	}

}
