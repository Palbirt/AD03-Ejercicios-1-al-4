package ud3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;


public class JDBC_insert_java {
	
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
		
		try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			s = c.createStatement();
			int nFil = s.executeUpdate("INSERT INTO CLIENTES (DNI, APELLIDOS, CP) VALUES "
					+ "('78901234X', 'NADAL', '44126'),"
					+ "('89012345E', 'ROJAS', null),"
					+ "('56789012B', 'SAMPER', '29730'),"
					+ "('09876543K', 'GARCIA', null);");
			
			System.out.println( nFil + " Filas insertadas");
		} catch (SQLException e) {
			muestraErrorSQL(e);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		finally {
			try {
				if( s !=null ) s.close();
				if( c !=null ) c.close();				
			} catch(Exception ex) {}
		}

	}

}
