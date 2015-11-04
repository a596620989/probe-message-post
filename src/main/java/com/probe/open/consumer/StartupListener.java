package com.probe.open.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * entrance point, response for start the consumer.
 * 
 * @author eros
 *
 */
@Component
public class StartupListener implements ApplicationListener<org.springframework.context.event.ContextRefreshedEvent> {

	@Autowired
	private ConsumerClient consumerClient;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		consumerClient.startup();
	}

}