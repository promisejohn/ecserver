package org.anyio.b2c.config;

import java.util.Arrays;
import java.util.List;

import org.anyio.b2c.boot.BootMessageListener;
import org.anyio.b2c.boot.BootMessageListenerPojo;
import org.anyio.b2c.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Autowired
	private JedisConnectionFactory jedisConnFactory;

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
	public RedisMessageListenerContainer redisMessageListenerContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(jedisConnFactory);
		List<ChannelTopic> topics = Arrays.asList(new ChannelTopic("new"), new ChannelTopic("update"),
				new ChannelTopic("remove"));
		container.addMessageListener(messageListenerAdapter(), topics);
		container.addMessageListener(bootMessageListener(), topics);
		return container;
	}
}
