package ud3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBC_transacciones {

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
			
			c.setAutoCommit(false);
			
			int i = 0;
			sInsert.setString(++i, "54320198V");
			sInsert.setString(++i, "CARVAJAL");
			sInsert.setInt(++i, 10109);
			sInsert.executeUpdate();
			
			sInsert.setString(i=1, "76543210S");
			sInsert.setString(++i, "MARQUEZ");
			sInsert.setInt(++i, 46987);
			sInsert.executeUpdate();
			
			sInsert.setString(i=1, "90123456A");
			sInsert.setString(++i, "MOLINA");
			sInsert.setInt(++i, 35153);
			sInsert.executeUpdate();
			
			c.commit();
			
		} catch (SQLException e) {
			muestraErrorSQL(e);
			try {
				c.rollback();
			} catch (Exception er ) {
				System.err.println("ERROR haciendo ROLLBACK");
				e.printStackTrace(System.err);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.err.println("ERROR de conexión");
		}
		finally {
			try {
				if( sInsert != null ) sInsert.close();
				if( c != null ) c.close();
			} catch(Exception ex) {}
		}


	}

}