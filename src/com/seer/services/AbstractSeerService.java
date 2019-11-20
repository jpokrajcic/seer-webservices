package com.seer.services;

import com.seer.common.ErrorCodes;
import com.seer.exception.SeerException;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class AbstractSeerService {

	protected SimpleJdbcTemplate simpleJdbcTemplate;
	protected DriverManagerDataSource dataSource;
	protected DriverManagerDataSource dataSourceCropwire;
	protected DriverManagerDataSource dataSourceCropwireData;

	/**
	 * This constructor gets fired when DataSource configured by Spring
	 * @param dataSource
	 */
	public AbstractSeerService(DriverManagerDataSource dataSource) {
		Logger logger = Logger.getLogger(AbstractSeerService.class);
		
		this.dataSource = dataSource;		
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		
		logger.info("SimpleJdbcTemplate initialized");
		
		if (dataSource == null || simpleJdbcTemplate == null) {
			logger.error("SimpleJdbcTemplate initialization Error");
		}
    }	
	
	/**
	 * This method should be called from any service method that requires authenticated user.
	 * It returns authenticated Subject, otherwise throws SeerException.
	 * @param sessionId
	 * @return
	 * @throws SeerException
	 */
	protected Subject getSubject(String sessionId) throws SeerException {
		Subject requestSubject = null;
		Session session = null;
		try {
			//returns Subject for sessionId and if session doesn't exist WILL NOT CREATE NEW SESSION
			requestSubject = new Subject.Builder().sessionCreationEnabled(false).sessionId(sessionId).buildSubject();
			session = requestSubject.getSession(false);			
		}
		catch (Exception ex) {
			throw new SeerException(ErrorCodes.NOT_AUTHENTICATED, "Not authenticated");
		}
		if (session == null) {
			throw new SeerException(ErrorCodes.NOT_AUTHENTICATED, "Not authenticated");
		}
		return requestSubject;
	}
}
