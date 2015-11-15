package org.anyio.b2c.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages={"org.anyio.b2c.domain.jpa"})
@EnableJpaRepositories(basePackages = {"org.anyio.b2c.repository.jpa"})
@EnableTransactionManagement
public class JpaConfig {

}
