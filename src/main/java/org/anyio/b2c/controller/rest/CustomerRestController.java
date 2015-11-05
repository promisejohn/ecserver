package org.anyio.b2c.controller.rest;

import org.anyio.b2c.domain.Customer;
import org.anyio.b2c.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;
	
	private Logger log = LoggerFactory.getLogger(CustomerRestController.class);

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Customer> getCustomers() {
		log.debug("API : getCustomers");
		return customerService.listAllCustomer();
	}
	
	
}