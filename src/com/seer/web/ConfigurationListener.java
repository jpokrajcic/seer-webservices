package com.seer.web;

import com.seer.config.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConfigurationListener implements ServletContextListener
{
	public void contextDestroyed(ServletContextEvent arg0)
	{
	}
	
	public void contextInitialized(ServletContextEvent arg0)
	{
	 Configuration.initialize(arg0);
	}
}