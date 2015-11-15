package org.anyio.b2c.boot;

import javax.annotation.Resource;

import org.anyio.b2c.domain.jpa.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisBoot implements CommandLineRunner {

	private final Logger log = LoggerFactory.getLogger(RedisBoot.class);
	
	@Autowired
	private RedisTemplate redisTemplate;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, String> valueOps;

	@Autowired
	private JedisConnectionFactory jedisConnFactory;

	@Override
	public void run(String... args) throws Exception {
		listOps.leftPush("hello", "world");
		valueOps.append("hi", "redis");
		RedisTemplate<String, Customer> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Customer.class));
		redisTemplate.setConnectionFactory(jedisConnFactory);
		redisTemplate.afterPropertiesSet();
		
		redisTemplate.opsForValue().set("prc", new Customer("testredis", "testredis@email.com", "testredis139"));
		Customer c = redisTemplate.opsForValue().get("prc");
		log.debug(String.format("Get key 'prc' from redis: %s\n", c));

		Customer[] list = { new Customer("testredislist1", "testredislist1", "testredislist1"),
				new Customer("testredislist2", "testredislist2", "testredislist2") };
		BoundListOperations<String, Customer> bops = redisTemplate.boundListOps("prclist");
		bops.rightPushAll(list);
		log.debug("list size: " + bops.size());
		
		redisTemplate.convertAndSend("new", "create some object msg send.");
		redisTemplate.convertAndSend("update", "update msg send.");
		redisTemplate.convertAndSend("remove", "remove msg send.");
	}

}
