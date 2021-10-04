package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TruncatePlayerDemo {
	public Statement stmt;
	public TruncatePlayerDemo() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			// handle the error
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/plantsvszombies?"
					+ "user=root&password=0313&serverTimezone=UTC&useSSL=false");

			stmt = conn.createStatement();
			
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
}
