package org.beansaver;

public interface IEntityHandler {

	/**
	 * @param entity Inserts the entity
	 */
	public void insert(Object entity);
	
	/**
	 * @param entity Updates the entity
	 */
	public void update(Object entity);
	
	/**
	 * @param entity Inserts or updates the entity
	 */
	public void insertOrUpdate(Object entity);
	
	/**
	 * @param entity Deletes the entity
	 */
	public void delete(Object entity);
	
	/**
	 * @param primaryKey PK
	 */
	public void get(Object primaryKey);
	
}
