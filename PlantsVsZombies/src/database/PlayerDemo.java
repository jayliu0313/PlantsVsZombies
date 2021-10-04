package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerDemo {

	public ResultSet rs;
	
	public PlayerDemo() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			// handle the error
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/plantsvszombies?"
					+ "user=root&password=0313&serverTimezone=UTC&useSSL=false");

			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT P.*, GameID FROM player P");

			while (rs.next()) {
				System.out.println(
						rs.getInt(1) + "\t");
			}

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
}