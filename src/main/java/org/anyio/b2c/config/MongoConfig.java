package org.anyio.b2c.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = { "org.anyio.b2c.repository.mongo" })
public class MongoConfig {

}
