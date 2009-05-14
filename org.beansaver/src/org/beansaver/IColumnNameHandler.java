package org.beansaver;

import javax.sql.DataSource;

/**
 * Responsible for mapping a property name to a DB column
 * @author Jacek Furmankiewicz
 */
public interface IColumnNameHandler extends IDbHandler {
	
	/**
	 * @param entityType Entity type
	 * @param propertyName Property name
	 * @param propertyType Property type
	 * @param ds Datasource (may be null if not connected)
	 * @return DB column name
	 */
	public String getColumnName(Class<?> entityType, String propertyName, Class<?> propertyType, DataSource ds);

}
