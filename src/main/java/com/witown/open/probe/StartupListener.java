package com.witown.open.probe;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.witown.probe.web.service.ProbeService;

@Component
public class StartupListener implements ApplicationListener<org.springframework.context.event.ContextRefreshedEvent> {
	
//	@Autowired
//	private OpenThirdInfoDAO openThirdInfoDAO;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		ProbeService service = event.getApplicationContext().getBean(ProbeService.class);
//		System.out.println(openThirdInfoDAO);
//		System.out.println(ApplicationHelper.getApplicationContext().getBean(ProbeService.class));
//		System.out.println(service);
//		System.out.println(probeService);
	}

}