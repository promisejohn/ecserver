package org.anyio.b2c.controller;

import org.anyio.b2c.domain.Customer;
import org.anyio.b2c.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	private Logger log = LoggerFactory.getLogger(CustomerController.class);

	@RequestMapping(value = "customersall", method = RequestMethod.GET)
	public String listCustomers(Model model) {
		model.addAttribute("customers", customerService.listAllCustomer());
		log.debug("show customers all controller.");
		return "customers";
	}

	@RequestMapping(value = "customers", method = RequestMethod.GET)
	public String showCustomers(Model model, Pageable pageable) {
		
		// todo: add page number validation
		
		Page<Customer> page = customerService.listCustomers(pageable);
		// prevent page number exceeds totalPages.
		int current_index = Math.min(page.getNumber(), page.getTotalPages() - 1);
		// start from the second item.
		int start_index = Math.min(1, page.getTotalPages() - 2);
		// end with the item before the last
		int end_index = Math.min(9, page.getTotalPages() - 2);

		if (current_index >= 10) {// pagenum >= current_index
			start_index = Math.max(1, current_index - 3);
			end_index = Math.min(page.getTotalPages() - 2, current_index + 2);
		}

		model.addAttribute("customers", page);
		model.addAttribute("current_index", current_index);
		model.addAttribute("start_index", start_index);
		model.addAttribute("end_index", end_index);

		log.debug("show customers with page.");
		return "customers";
	}

	@RequestMapping(value = "customer/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("customer", customerService.getCustomerById(id));
		return "customershow";
	}

	@RequestMapping(value = "customer/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("customer", customerService.getCustomerById(id));
		return "customerform";
	}

	@RequestMapping(value = "customer/new")
	public String newCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "customerform";
	}

	@RequestMapping(value = "customer", method = RequestMethod.POST)
	public String saveCustomer(Customer customer) {
		customerService.saveCustomer(customer);
		return "redirect:customer/" + customer.getId();
	}

	@RequestMapping(value = "customer/delete/{id}")
	public String delete(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return "redirect:/customers";
	}
}
