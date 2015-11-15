package org.anyio.b2c.config;

import org.anyio.b2c.domain.mongo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.MongoClient;

public class MongodbTest {

	private static final Logger log = LoggerFactory.getLogger(MongodbTest.class);

	public static void main(String[] args) throws Exception {
		MongoOperations mongoOps = new MongoTemplate(new MongoClient("localhost"), "database");
		mongoOps.insert(new Person("joe", 34));
		log.info(mongoOps.findOne(new Query(where("name").is("joe")), Person.class).toString());

	}

}
