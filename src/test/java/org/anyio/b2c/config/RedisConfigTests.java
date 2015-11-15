package org.anyio.b2c.config;

import org.anyio.b2c.EcserverApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { EcserverApplication.class })
@EnableCaching
@Configuration
public class RedisConfigTests {

	private static final Logger log = LoggerFactory.getLogger(RedisConfigTests.class);

	@Cacheable("customercache")
	public String calculateResult() {
		log.debug("Performing expensive calculation...");
		// perform computationally expensive calculation
		return "result";
	}

//	@Bean
//	public CacheManager cacheManager() {
//		return new ConcurrentMapCacheManager("customercache");
//	}

	@Test
	public void testRedisCache() {
		calculateResult();
		calculateResult();
	}

}
