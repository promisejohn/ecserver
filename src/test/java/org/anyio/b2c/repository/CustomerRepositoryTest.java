package org.anyio.b2c.repository;

import static org.junit.Assert.*;

import org.anyio.b2c.config.PersistConfig;
import org.anyio.b2c.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PersistConfig.class})
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void testSaveCustomer() {
		Customer c = new Customer();
		c.setName("hello");
		assertNull(c.getId());
		customerRepository.save(c);
		assertNotNull(c.getId());
		
		Customer cfetch = customerRepository.findOne(c.getId());
		assertNotNull(cfetch);
		assertEquals(c.getId(), cfetch.getId());
		
		long cCount = customerRepository.count();
		assertEquals(1, cCount);
	}

}
