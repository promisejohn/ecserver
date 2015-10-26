package org.anyio.b2c.controller;

import org.anyio.b2c.domain.Customer;
import org.anyio.b2c.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "customers", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("customers", customerService.listAllCustomer());
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
