package org.audiolord.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Represents the database engine
 * 
 * @author Jacek Furmankiewicz
 * 
 */
@Repository
public class Database {

	private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);

	private JdbcTemplate template;

	/**
	 * @param dataSource
	 *            Data source
	 * @throws IOException 
	 */
	@Autowired(required=true)
	public void setDataSource(DataSource dataSource) throws IOException {
		System.out.println("TEST");
		template = new JdbcTemplate(dataSource);
		init();
	}

	/**
	 * Initializes the database
	 * @throws IOException 
	 */
	private void init() throws IOException {
		try {
			template.queryForInt("SELECT Count(*) from library");
		} catch (Exception ex) {
			// DB not created
			createDatabase();
		}
	}

	// creates the database
	private void createDatabase() throws IOException  {
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
		
		template.execute(bld.toString());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return template.getDataSource().getClass().getSimpleName();
	}


}
