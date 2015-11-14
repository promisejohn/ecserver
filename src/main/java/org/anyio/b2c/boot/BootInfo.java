package org.anyio.b2c.boot;

import java.util.Arrays;

import org.anyio.b2c.EcserverApplication;
import org.anyio.b2c.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BootInfo implements CommandLineRunner {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private ApplicationConfig applicationConfig;

	private final Logger log = LoggerFactory.getLogger(EcserverApplication.class);

	@Override
	public void run(String... args) throws Exception {
		String[] beanNames = context.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		log.debug("Inspect the beans loaded by Spring Boot: \n" + String.join("\n", beanNames));
		log.debug(String.format("Secret: %s", applicationConfig.getSecret()));
	}

}
