package org.anyio.b2c.config;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.anyio.b2c.boot.BootMessageListener;
import org.anyio.b2c.boot.BootMessageListenerPojo;
import org.anyio.b2c.domain.jpa.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

	@Value("${application.redis.expire.customercache}")
	private Long expireSeconds;

	@Bean
	public StringRedisSerializer stringRedisSerializer() {
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		return stringRedisSerializer;
	}

	@Bean
	public Jackson2JsonRedisSerializer<Customer> jacksonJsonRedisJsonSerializer() {
		Jackson2JsonRedisSerializer<Customer> jacksonJsonRedisJsonSerializer = new Jackson2JsonRedisSerializer<>(
				Customer.class);
		return jacksonJsonRedisJsonSerializer;
	}

	@Bean
	public BootMessageListener bootMessageListener() {
		return new BootMessageListener();
	}

	@Bean
	public MessageListenerAdapter messageListenerAdapter() {
		MessageListenerAdapter adapter = new MessageListenerAdapter(new BootMessageListenerPojo());
		adapter.setSerializer(stringRedisSerializer());
		adapter.setDefaultListenerMethod("handleMessage");
		return adapter;
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(JedisConnectionFactory jedisConnFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(jedisConnFactory);
		List<ChannelTopic> topics = Arrays.asList(new ChannelTopic("new"), new ChannelTopic("update"),
				new ChannelTopic("remove"));
		container.addMessageListener(messageListenerAdapter(), topics);
		container.addMessageListener(bootMessageListener(), topics);
		return container;
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object o, Method method, Object... objects) {
				// This will generate a unique key of the class name, the method
				// name,and all method parameters appended.
				StringBuilder sb = new StringBuilder();
				sb.append(o.getClass().getName());
				sb.append(method.getName());
				for (Object obj : objects) {
					sb.append(obj.toString());
				}
				log.debug(sb.toString());
				return sb.toString();
			}
		};
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
		StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);

		// won't work on hibernate objects with cglib proxy default config.
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

		redisTemplate.afterPropertiesSet();

		RedisCacheManager cm = new RedisCacheManager(redisTemplate);
		log.debug("Redis cache expire in {} seconds.", expireSeconds);
		cm.setDefaultExpiration(Duration.ofSeconds(expireSeconds).getSeconds());
		return cm;
	}
}
