package org.anyio.b2c;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcserverApplication {

	private static final Logger log = LoggerFactory.getLogger(EcserverApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EcserverApplication.class, args);
		log.info("Bootup over.");
	}
}
