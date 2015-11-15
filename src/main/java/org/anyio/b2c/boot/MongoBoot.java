package org.anyio.b2c.boot;

import org.anyio.b2c.domain.mongo.Person;
import org.anyio.b2c.repository.mongo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoBoot implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(MongoBoot.class);

	@Autowired
	private PersonRepository personRepository;

	@Override
	public void run(String... args) throws Exception {

		personRepository.deleteAll();
		personRepository.save(new Person("Alice", 20));
		personRepository.save(new Person("Bob", 22));

		for (Person person : personRepository.findAll()) {
			log.debug(person.toString());
		}

		log.debug("Mongo bootup over.");
	}

}
