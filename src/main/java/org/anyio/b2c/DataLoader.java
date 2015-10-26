package org.anyio.b2c;

import org.anyio.b2c.domain.Customer;
import org.anyio.b2c.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private CustomerRepository customerRepository;

	private Logger log = LoggerFactory.getLogger(DataLoader.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Customer c1 = new Customer();
		c1.setName("autogen_customer1");
		c1.setStatus(Customer.Status.NORMAL);
		c1.setMobile("1111111111");
		customerRepository.save(c1);
		log.info("Saved autogen_customer with id: " + c1.getId());

		Customer c2 = new Customer();
		c2.setName("autogen_customer2");
		c2.setStatus(Customer.Status.NORMAL);
		c2.setMobile("2222222222");
		customerRepository.save(c2);
		customerRepository.save(c2);
		log.info("Saved autogen_customer with id: " + c2.getId());
	}

}
