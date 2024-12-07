package evaluacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio1 {

	public static void muestraErrorSQL(SQLException e) {
		System.err.println("SQL ERROR mensaje: " + e.getMessage());
		System.err.println("SQL Estado: " + e.getSQLState());
		System.err.println("SQL código específico: " + e.getErrorCode());
	}

	public static void main(String[] args) {
		
		String basedatos = "dbeventos";
		String host = "localhost";
		String port = "3306";
		String parAdic = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDateTimeCode=false&serverTimezone=UTC";
		String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
		String user = "ad03";
		String pwd = "ad03password";
		
		Connection c = null;
		Statement s = null;
		ResultSet rs = null;
		
		try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			s = c.createStatement();
			rs = s.executeQuery("SELECT e.nombre_evento, u.nombre, u.direccion, count(ae.dni) as 'asistentes' "
					+ " FROM eventos e, ubicaciones u, asistentes_eventos ae "
					+ " where e.id_ubicacion = u.id_ubicacion and ae.id_evento = e.id_evento "
					+ " group by e.id_evento ");
			
			
			// Imprimir header
	        System.out.println("| Evento                         | Asistentes | Ubicación                           | Dirección                     |");
	        System.out.println("+--------------------------------+------------+-------------------------------------+--------------------------------");
			
	        // Imprimir datos de la tabla
			while ( rs.next() ) {
				System.out.printf("| %-30s | %-10s | %-35s | %-29s |\n", rs.getString("nombre_evento"), rs.getString("asistentes"), rs.getString("nombre"), rs.getString("direccion"));
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
