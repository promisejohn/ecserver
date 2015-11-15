package org.anyio.b2c.boot;

import org.anyio.b2c.domain.jpa.Customer;
import org.anyio.b2c.repository.jpa.CustomerRepository;
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

		for (int i = 0; i < 20; i++) {
			Customer c = new Customer("测试用户" + i, "user" + i + "@mail.anyio.org", "mobile" + i);
			customerRepository.save(c);
			log.info("Saved " + c.getName() + " with id: " + c.getId());
		}
	}

}
