package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowPlayerDemo {

	public ResultSet rs;
	private Statement stmt;
	private Connection conn = null;

	public ShowPlayerDemo() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			// handle the error
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/plantsvszombies?"
					+ "user=root&password=0313&serverTimezone=UTC&useSSL=false");

			stmt = conn.createStatement();

			rs = stmt.executeQuery("select p.* from player p order by Score desc");
			
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}