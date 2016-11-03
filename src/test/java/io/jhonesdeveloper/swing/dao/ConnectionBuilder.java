/*
 * ConnectionBuilder.java.java
 *
 * Copyright 2016 Caique Jhones
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.jhonesdeveloper.swing.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * @author Caique Jhones
 */
public final class ConnectionBuilder {
	
	private final static String DATABASE_URL = "jdbc:h2:mem:dbtest";
	
	private ConnectionBuilder() {
	}
	
	public static ConnectionSource connectionSource(Class<?>... classes) throws SQLException {
		ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);
		assertNotNull(connectionSource);
		
		createTables(connectionSource, classes);
		return connectionSource;
	}
	
	private static void createTables(ConnectionSource connectionSource, Class<?>... classes) throws SQLException {
		for (Class<?> clazz : classes) {
			TableUtils.createTableIfNotExists(connectionSource, clazz);
		}
	}
	
}
