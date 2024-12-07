package ud3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBC_prepared_statement {
	
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
		PreparedStatement sInsert = null;
		
		try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			sInsert = c.prepareStatement("INSERT INTO CLIENTES(DNI,APELLIDOS,CP) VALUES (?,?,?);");
			
			sInsert.setString(1, "78901231A");
			sInsert.setString(2, "PEREZ");
			sInsert.setInt(3, 46123);
			
			sInsert.executeUpdate();
			
			
		} catch (SQLException e) {
			muestraErrorSQL(e);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		finally {
			try {
				if( sInsert != null ) sInsert.close();
				if( c != null ) c.close();
			} catch(Exception ex) {}
		}


	}

}
