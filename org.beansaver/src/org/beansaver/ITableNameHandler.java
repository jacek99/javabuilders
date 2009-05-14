package org.beansaver;

import javax.sql.DataSource;

/**
 * Responsible for looking up a table name for a POJO
 * @author Jacek Furmankiewicz
 */
public interface ITableNameHandler extends IDbHandler {

	/**
	 * @param type Entity type
	 * @param ds Current datasource (may be null if not connected)
	 * @return DB table name for the entity type
	 */
	public String getTableName(Class<?> type, DataSource ds);
}
