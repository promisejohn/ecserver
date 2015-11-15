package org.anyio.b2c.repository.mongo;

import java.util.List;

import org.anyio.b2c.domain.mongo.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

	Person findByName(String name);

	List<Person> findByAgeBetween(int from, int to);
}
