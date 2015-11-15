package org.anyio.b2c.repository.mongo;

import java.util.List;

import org.anyio.b2c.domain.mongo.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "person", path = "person")
public interface PersonRepository extends MongoRepository<Person, String> {

	// when use @RepositoryRestResource to expose repository methods to rest,
	// use @Param to bind query data from rest.
	Person findByName(@Param(value = "name") String name);

	List<Person> findByAgeBetween(int from, int to);
}
