package org.anyio.b2c.repository;

import java.util.List;

import org.anyio.b2c.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long>, QueryDslPredicateExecutor<Customer> {

	List<Customer> findByName(String name);

	List<Customer> findByNameContainsOrEmailContainsAllIgnoreCase(String name, String email, Pageable pageRequest);

	@Query("SELECT c FROM Customer c WHERE " + "LOWER(c.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR "
			+ "LOWER(c.email) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	List<Customer> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageRequest);

	@Query("SELECT c FROM Customer c WHERE " + "LOWER(c.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR "
			+ "LOWER(c.email) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	Page<Customer> findBySearchTermPage(@Param("searchTerm") String searchTerm, Pageable pageRequest);

	@Query("SELECT c FROM Customer c WHERE " + "LOWER(c.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR "
			+ "LOWER(c.email) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	Slice<Customer> findBySearchTermSlice(@Param("searchTerm") String searchTerm, Pageable pageRequest);

}
