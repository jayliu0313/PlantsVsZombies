package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddPlayerDemo {
	
	public int ID = 0;
	public String name;
	public int score;
	public int sunScore;
	public PreparedStatement sql;
	
	public AddPlayerDemo() {
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
			
			sql = conn.prepareStatement("INSERT INTO player(GameID, Name, Score, SunScore) VALUES (?, ?, ?, ?)");
		
			ResultSet rs = stmt.executeQuery("SELECT P.* FROM player P");

//			while (rs.next()) {
//				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
//			}

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}