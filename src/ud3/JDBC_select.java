package ud3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class JDBC_select {
	
	public static void muestraErrorSQL(SQLException e) {
		System.err.println("SQL ERROR mensaje: " + e.getMessage());
		System.err.println("SQL Estado: " + e.getSQLState());
		System.err.println("SQL código específico: " + e.getErrorCode());
	}

	public static void main(String[] args) {
		
		String basedatos = "test";
		String host = "localhost";
		String port = "3306";
		String parAdic = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDateTimeCode=false&serverTimezone=UTC";
		String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
		String user = "test";
		String pwd = "test";
		
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM CLIENTES");
			
			int i=1;
			while ( rs.next() ) {
				System.out.println("[" + (i++) + "]");
				System.out.println("DNI: " + rs.getString("DNI"));
				System.out.println("Apellidos: " + rs.getString("APELLIDOS"));
				System.out.println("CP: " + rs.getString("CP"));
			}
			
		} catch (SQLException e) {
			muestraErrorSQL(e);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		finally {
			try {
				if( rs !=null ) rs.close();
				if( s !=null ) s.close();
				if( c !=null ) c.close();
			} catch(Exception ex) {}
		}

	}

}
