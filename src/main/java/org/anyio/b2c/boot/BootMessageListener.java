package org.anyio.b2c.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;

public class BootMessageListener implements MessageListener {

	private final Logger log = LoggerFactory.getLogger(BootMessageListener.class);

	@Autowired
	private RedisSerializer<String> stringSerializer;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		log.debug("Receive message: {} on channel: {}", stringSerializer.deserialize(message.getBody()),
				stringSerializer.deserialize(message.getChannel()));
	}

}
