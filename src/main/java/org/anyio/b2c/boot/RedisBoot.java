package org.anyio.b2c.boot;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisBoot implements CommandLineRunner {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	@Override
	public void run(String... args) throws Exception {
		listOps.leftPush("hello", "world");
//		redisTemplate.boundListOps("hello").leftPush("world");
	}

}
