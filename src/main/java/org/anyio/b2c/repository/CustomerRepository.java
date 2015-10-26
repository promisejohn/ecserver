package org.anyio.b2c.repository;

import java.util.List;

import org.anyio.b2c.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByName(String name);
}
