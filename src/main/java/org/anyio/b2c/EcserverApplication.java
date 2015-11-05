package org.anyio.b2c;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EcserverApplication {

	private static final Logger log = LoggerFactory.getLogger(EcserverApplication.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(EcserverApplication.class, args);
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		log.debug("Inspect the beans loaded by Spring Boot: " + String.join("\n", beanNames));
	}
}
