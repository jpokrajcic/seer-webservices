package com.seer.config;

import com.seer.utils.TypedProperties;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.io.IOException;

public class Configuration {
	private static Configuration config;

	private TypedProperties propsGeneral = new TypedProperties();
	private TypedProperties propsQueries = new TypedProperties();

	public TypedProperties 	getQueries () { return propsQueries; }
	public TypedProperties 	getProps () { return propsGeneral; }

    private ServletContext servletContext;

	public static Configuration get()
	{
		return config;
	}
	
	public static void initialize(ServletContextEvent ctx) {
		try {
			config = new Configuration(ctx);
		} catch (Exception ex) {
			BasicConfigurator.configure();
			Logger.getLogger(Configuration.class).error("***************************************************");
			Logger.getLogger(Configuration.class).error("INITIALIZATION PROBLEM, CHECK YOUR CONFIGURATION!!!");
			Logger.getLogger(Configuration.class).error("***************************************************");
			Logger.getLogger(Configuration.class).error("Exception: ", ex);
		}
	}
	
	private Configuration(ServletContextEvent ctx) throws Exception {
        this.servletContext = ctx.getServletContext();

        String appConfigPath = "/WEB-INF/config/";

        String configFilePath = appConfigPath + "seer-config.xml";
        String configSqlCrudFilePath = appConfigPath + "seer-config-sql-crud.xml";


        propsGeneral.loadFromXML(servletContext.getResourceAsStream(configFilePath));

        propsQueries.loadFromXML(servletContext.getResourceAsStream(configSqlCrudFilePath));

        propsGeneral = propsGeneral.trimProperties();
        propsQueries = propsQueries.trimProperties();

        initLogger (appConfigPath + propsGeneral.getString("seer.log4j.xml"));
	}

	private void initLogger(String log4FilePath) {
        DOMConfigurator domConfigurator = new DOMConfigurator();
        Logger logger = Logger.getRootLogger();
        logger.removeAllAppenders();
        try {
            domConfigurator.doConfigure(servletContext.getResourceAsStream(log4FilePath), LogManager.getLoggerRepository());
            logger.info("Logger initialized");
        } catch(Exception exception) {
            logger.addAppender(new ConsoleAppender(new PatternLayout("%d %5p: %m%n"), ConsoleAppender.SYSTEM_OUT));
            logger.setLevel(Level.DEBUG);
            logger.warn("Failed to configure logging to file \" + " + log4FilePath + "\": " + exception.getMessage());
        }
	}
}
