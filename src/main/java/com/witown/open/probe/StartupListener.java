package com.witown.open.probe;

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

	// @Autowired
	// private OpenThirdInfoDAO openThirdInfoDAO;
	@Autowired
	private ConsumerClient consumerClient;

	public void onApplicationEvent(ContextRefreshedEvent event) {
//		consumerClient.startup();
		// ProbeService service =
		// event.getApplicationContext().getBean(ProbeService.class);
		// System.out.println(openThirdInfoDAO);
		// System.out.println(ApplicationHelper.getApplicationContext().getBean(ProbeService.class));
		// System.out.println(service);
		// System.out.println(probeService);
	}

}