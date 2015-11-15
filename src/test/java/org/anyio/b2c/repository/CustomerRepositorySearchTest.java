package org.anyio.b2c.repository;

import org.anyio.b2c.config.JpaConfig;
import org.anyio.b2c.domain.jpa.QCustomer;
import org.anyio.b2c.repository.jpa.CustomerRepository;
import org.anyio.b2c.domain.jpa.Customer;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysema.query.types.Predicate;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { JpaConfig.class })
public class CustomerRepositorySearchTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Before
	public void setUp() {
		customerRepository.deleteAll();
		for (int i = 0; i < 3; i++) {
			Customer c = new Customer("test" + i, "test" + i + "@anyio.org", "test" + i + "mobile");
			Customer cc = new Customer("test" + i, "test" + i + "@anyio.org", "test" + i + "mobile");
			customerRepository.save(c);
			customerRepository.save(cc);
		}
	}

	@Test
	public void testSearchByNameAndEmailAndMobile() {
		QCustomer customer = QCustomer.customer;
		Predicate namePred = customer.name.eq("test1");
		Predicate emailPred = customer.email.eq("test2@anyio.org");
		Predicate mobilePred = customer.mobile.eq("test0mobile");
		assertThat(customerRepository.findAll(namePred).iterator().next().getName(), equalTo("test1"));
		assertThat(customerRepository.findOne(customer.name.eq("test1").and(mobilePred)), nullValue());
		assertThat(IteratorUtils.toList(customerRepository.findAll(customer.name.eq("test1").or(emailPred)).iterator())
				.size(), equalTo(4));
	}

	@Test
	public void testSearchByNamePage() {
		QCustomer customer = QCustomer.customer;
		Page<Customer> page = customerRepository.findAll(customer.name.eq("test1"), new PageRequest(0, 1));
		assertThat(page.getTotalPages(), equalTo(2));
		assertThat(page.getContent().size(),equalTo(1));
		assertThat(page.getTotalElements(),equalTo(2L));
	}
}
