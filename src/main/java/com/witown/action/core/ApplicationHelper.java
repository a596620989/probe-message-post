package com.witown.action.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationHelper implements ApplicationContextAware {  
  
      
    private static ApplicationContext applicationContext; 
      
    public void setApplicationContext(ApplicationContext context)  
            throws BeansException {  
            applicationContext = context;  
    }  
  
      
    public static  ApplicationContext getApplicationContext(){
        return applicationContext;  
    }  
}  