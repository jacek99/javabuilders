package org.beansaver;

import javax.sql.DataSource;

import org.beansaver.util.NameUtils;

/**
 * Default database handler
 * @author jacek
 *
 */
public class DefaultDbHandler implements IDbHandler, ITableNameHandler, IColumnNameHandler,
	ITimestampHandler {

	public String getTableName(Class<?> type, DataSource ds) {
		return NameUtils.convertPropertyName(propertyName, separator)type.getSimpleName().toUpperCase();
	}

	public String getColumnName(Class<?> entityType, String propertyName, Class<?> propertyType, DataSource ds) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isTimestampRequired(Class<?> entityType, String propertyName, Class<?> propertyType, Operation operation,
			DataSource ds) {
		// TODO Auto-generated method stub
		return false;
	}

}
