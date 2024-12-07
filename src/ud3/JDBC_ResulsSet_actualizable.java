package ud3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_ResulsSet_actualizable {

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
		Statement sConsulta = null;
		ResultSet rs = null;
		
		try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			sConsulta = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = sConsulta.executeQuery("SELECT * FROM CLIENTES WHERE CP IS NOT NULL");
			
			c.setAutoCommit(false);
			
			rs.last(); // Modifica ultimo cliente
			rs.updateString("CP", "02568");
			rs.updateRow();

			rs.previous(); // Borrar penúltimo cliente
			rs.deleteRow();
			
			rs.moveToInsertRow(); // Insertar nueva fila
			rs.updateString("DNI", "24862486S");
			rs.updateString("APELLIDOS", "ZURITA");
			rs.updateString("CP", "33983");
			rs.insertRow();
			
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
		}
		finally {
			try {
				if( rs !=null ) rs.close();
				if( sConsulta !=null ) sConsulta.close();
				if( c !=null ) c.close();
			} catch(Exception ex) {}
		}

	}

}
