package com.witown.action.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 */
public class SimpleHandlerInterceptor extends HandlerInterceptorAdapter {
    
	protected Logger            logger           = LoggerFactory.getLogger(getClass()); 

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

	    logger.debug("enter interceptor");
		
		return true;
	}

}
