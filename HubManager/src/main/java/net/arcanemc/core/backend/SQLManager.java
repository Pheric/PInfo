package net.arcanemc.core.backend;

import java.sql.*;

public class SQLManager {

	private Connection connection;

	public SQLManager(String host, String user, String pass, String db, int port) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port +"/" + db + "?autoReconnect=true", user, pass);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}

	public PreparedStatement prepareStatement(String sql) {
		try {
			return connection.prepareStatement(sql);
		} catch (SQLException e) {
			return null;
		}
	}

	public ResultSet executeQuery(String sql) {
		PreparedStatement statement = prepareStatement(sql);
		try {
			return statement.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public int executeUpdate(String sql) {
		PreparedStatement statement = prepareStatement(sql);
		try {
			return statement.executeUpdate();
		} catch (SQLException e) {
			return -1;
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}