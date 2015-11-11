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
		/**
		 * Generally in a Spring MVC application you have both a
		 * ContextLoaderListener and DispatcherServlet. Both components create
		 * their own ApplicationContext which in turn both fire a
		 * ContextRefreshedEvent. The DispatcherServlet uses the
		 * ApplicationContext as created by the ContextLoaderListener as its
		 * parent. Events fired from child contexts are propagated to the parent
		 * context. Now if you have an ApplicationListener
		 * <ContextRefreshedEvent> defined in the root context (the one loaded
		 * by the ContextLoaderListener) it will receive an event twice.
		 */
		if (event.getApplicationContext().getParent() == null) {
			consumerClient.startup();
		}
	}

}