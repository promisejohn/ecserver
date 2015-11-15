package org.anyio.b2c.repository;

import org.anyio.b2c.EcserverApplication;
import org.anyio.b2c.domain.mongo.Person;
import org.anyio.b2c.repository.mongo.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { EcserverApplication.class })
public class PersonRepositoryTests {

	private static final Logger log = LoggerFactory.getLogger(PersonRepositoryTests.class);

	@Autowired
	private PersonRepository personRepository;

	@Before
	public void setUp() {
		personRepository.deleteAll();
		for (int i = 0; i < 10; i++) {
			Person p = new Person("test" + i, i);
			personRepository.save(p);
		}
	}

	@Test
	public void testFindByName() {
		Person p = personRepository.findByName("test1");
		assertThat(p.getAge(), equalTo(1));
	}

	@Test
	public void testFindByAgeBetween() {
		List<Person> list = personRepository.findByAgeBetween(5, 8);
		assertThat(list.size(), equalTo(2));
	}
}
