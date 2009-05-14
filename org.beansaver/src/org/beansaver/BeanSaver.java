package org.beansaver;

import java.sql.Connection;
import java.util.HashMap;

import javax.sql.DataSource;

public class BeanSaver implements IEntityHandler {

	private HashMap<Class<?>,IEntityHandler> handlers = new HashMap<Class<?>, IEntityHandler>();
	private IDbHandler dbHandler = null;
	private DataSource ds = null;
	private Connection conn = null;
	

	/* (non-Javadoc)
	 * @see org.beansaver.IEntityHandler#delete(java.lang.Object)
	 */
	public void delete(Object entity) {
	}

	/* (non-Javadoc)
	 * @see org.beansaver.IEntityHandler#insert(java.lang.Object)
	 */
	public void insert(Object entity) {
		
	}

	/* (non-Javadoc)
	 * @see org.beansaver.IEntityHandler#insertOrUpdate(java.lang.Object)
	 */
	public void insertOrUpdate(Object entity) {
		
	}

	/* (non-Javadoc)
	 * @see org.beansaver.IEntityHandler#update(java.lang.Object)
	 */
	public void update(Object entity) {
	
		
	}

	/* (non-Javadoc)
	 * @see org.beansaver.IEntityHandler#get(java.lang.Object)
	 */
	public void get(Object primaryKey) {
		
	}
	
	/**
	 * Sets the handler that is responsible for various mappins of entities to DBs and
	 * runtime persistence behavior
	 * @param dbHandler DB handler
	 */
	public void setDbHandler(IDbHandler dbHandler) {
		this.dbHandler = dbHandler;
	}

	/**
	 * @return the DataSource
	 */
	public DataSource getDs() {
		return ds;
	}

	/**
	 * @param ds the DataSource
	 */
	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * @return the DB connection
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * @param conn the DB connection
	 */
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	
}
