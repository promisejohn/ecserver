package org.anyio.b2c.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootMessageListenerPojo {

	private final Logger log = LoggerFactory.getLogger(BootMessageListenerPojo.class);

	public void handleMessage(String message, String channel) {
		log.debug("Received message: {} on channel: {}", message, channel);
	}
}
