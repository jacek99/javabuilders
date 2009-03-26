package org.audiolord.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.sf.persist.Persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the database engine
 * @author Jacek Furmankiewicz
 *
 */
public class Database {

	private static Connection connection = null;

	private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
	private static Persist persist = null;
	
	/**
	 * Initializes the database
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static Connection init() throws ClassNotFoundException, SQLException, IOException {
	    
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Loading SQLite JDBC Driver");
		}
		Class.forName("org.sqlite.JDBC");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Connecting/Creating database");
		}
	    connection = DriverManager.getConnection("jdbc:sqlite:audiolord.db");
	    
	    try {
	    	connection.prepareStatement("SELECT Count(*) from library").executeQuery();
	    } catch (SQLException ex) {
	    	//tables not created - create them
	    	createDatabase();
	    }
	    
	    persist = new Persist(connection);
	    
	    return connection;
	}
	
	//creates the database
	private static void createDatabase() throws IOException, SQLException {
		BufferedReader rdr = new BufferedReader(new InputStreamReader(Database.class.getResourceAsStream("createdb.sql")));
		StringBuilder bld = new StringBuilder();
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reading in DB creation script");
		}
		String line = rdr.readLine();
		while (line != null) {
			bld.append(line).append("\n");
			line = rdr.readLine();
		}
		rdr.close();
		PreparedStatement stmt = getConnection().prepareStatement(bld.toString());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating DB...");
		}
		stmt.execute();
	}
	
	/**
	 * Closes the DB
	 * @throws SQLException 
	 */
	public static void close() throws SQLException {
		if (connection != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Closing database");
			}
			connection.close();
		}
	}
	
	/**
	 * @return Database connection
	 */
	public static Connection getConnection() {
		return connection;
	}

	/**
	 * @return the Persist ORM
	 */
	public static Persist getPersist() {
		return persist;
	}
	
}
