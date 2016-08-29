package com.mum.carpooling.ultil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
	private static Connection dbConnection = null;

	public static Connection getConnection() {
		if (dbConnection != null) {
			return dbConnection;
		} else {
			try {
				InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
				Properties properties = new Properties();
				if (properties != null) {
					properties.load(inputStream);

					String dbDriver = properties.getProperty("com.mysql.jdbc.Driver");
					String connectionUrl = properties.getProperty("jdbc:mysql://localhost/carpoolingdb");
					String userName = properties.getProperty("root");
					String password = properties.getProperty("root");

					Class.forName("com.mysql.jdbc.Driver").newInstance();
					dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/carpoolingdb", "root", "root");
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				//e.printStackTrace();
			}
			return dbConnection;
		}
	}
}