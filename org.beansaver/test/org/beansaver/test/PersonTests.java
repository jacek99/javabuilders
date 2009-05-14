package org.beansaver.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

import org.beansaver.BeanSaver;
import org.beansaver.test.resources.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonTests {

	BeanSaver saver;
	
	@Before
	public void setUp() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:h2:mem:");
		saver = new BeanSaver();
		saver.setConnection(conn);
		
		String sql = "CREATE TABLE PERSON (" +
			"ID integer identity, FIRST_NAME VARCHAR(255) NOT NULL, " +
			"LAST_NAME VARCHAR(255) NOT NULL, BIRTH_DATE DATE NOT NULL" +
			")";
		conn.prepareCall(sql).execute();
	}
	
	@After
	public void tearDown() throws SQLException {
		saver.getConnection().prepareCall("DROP TABLE PERSON").execute();
		saver.getConnection().close();
		saver = null;
	}
	
	@Test
	public void insert() {
		Person person = new Person("John","Doe",Calendar.getInstance());
		
		assertNull(person.getId());
		saver.insert(person);
		assertNotNull("INSERT failed, PK did not get updated",person.getId());
		
	}
	
	@Test
	public void update() {
		
	}
	
	@Test
	public void delete() {
		
	}
	
	@Test
	public void get() {
		
	}
	
	@Test
	public void queryByName() {
		
	}
	
}
