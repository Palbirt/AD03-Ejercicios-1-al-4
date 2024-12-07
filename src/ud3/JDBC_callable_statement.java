package ud3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_callable_statement {
	
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
		CallableStatement s = null;
		ResultSet rs = null;
		
		try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			s = c.prepareCall("{call longitud_apellido(?,?)}");
			
			s.setString(1,"78901231A");
			s.registerOutParameter(2, java.sql.Types.INTEGER);
			
			s.execute();
			
			int longitud = s.getInt(2);
			System.out.println("=> Longitud del apellido: " + longitud);
		
		} catch (SQLException e) {
			muestraErrorSQL(e);
		} catch (Exception e) {
			System.err.println("ERROR de conexión");
			e.printStackTrace(System.err);
		} finally {
			try {
				if( rs != null ) rs.close();
				if( s != null ) s.close();
				if( c != null ) c.close();
			} catch(Exception ex) {}
		}

	}

}
