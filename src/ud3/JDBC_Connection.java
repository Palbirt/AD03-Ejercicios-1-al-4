package ud3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class JDBC_Connection {
	
	public static void muestraErrorSQL(SQLException e) {
		System.err.println("SQL ERROR mensaje: " + e.getMessage());
		System.err.println("SQL Estado: " + e.getSQLState());
		System.err.println("SQL código específico: " + e.getErrorCode());
	}

	public static void main(String[] args) {
		// 
		String basedatos = "test";
		String host = "localhost";
		String port = "3306";
		String parAdic = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDateTimeCode=false&serverTimezone=UTC";
		String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
		String user = "test";
		String pwd = "test";
		
		Connection c = null;
		
		// Class.forName("com.mysql.jdbc.Driver"); - No necesario desde SE 6.0
		// Class.forName("com.mysql.cj.jdbc.Driver"); // - Para MySQL 8.0, no necesario
		
		try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			System.out.println("Conexión realizada");
		} catch (SQLException e) {
			muestraErrorSQL(e);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		finally {
			try {
				if( c !=null ) c.close();
			} catch(Exception ex) {}
		}

	}

}
