package org.beansaver;

import javax.sql.DataSource;

/**
 * Defines hot Timestamp columns should be handled
 * @author Jacek Furmankiewicz
 *
 */
public interface ITimestampHandler {

	/**
	 * @param entityType Entity type
	 * @param propertyName Property name
	 * @param propertyType Property type
	 * @param operation Current operation
	 * @param ds Datasource
	 * @return True if timestamp value should be generated, false if not
	 */
	public boolean isTimestampRequired(Class<?> entityType, String propertyName, Class<?> propertyType, Operation operation, DataSource ds);
	
}
