package rmibank.code.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private Connection conn = null;
	
	public Database(int agNumber) throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver JDBC não encontrado!");
		}

		if(conn == null) {
			String url = "jdbc:postgresql://localhost:5432/agency"+agNumber;
			String user = "postgres";
			String password = "21361qpo";
			conn = DriverManager.getConnection(url, user, password);			
		}
	}
	
	public Connection getConn() {
		return conn;
	}
}