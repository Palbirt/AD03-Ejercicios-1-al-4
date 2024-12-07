package evaluacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Ejercicio2 {

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
		int nuevaCapacidad = 0;
	
		Scanner scanner = new Scanner(System.in);
	
		// Pedir el nombre de la ubicación
		System.out.println("Introduce el nombre de la ubicación:");
        String nombreUbicacion = scanner.nextLine();
        
        
        try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			s = c.createStatement();
			rs = s.executeQuery("SELECT * from ubicaciones where nombre = '" + nombreUbicacion + "'");
			
			// Comprobar si la ubicación existe
			if( !rs.next() ) {
				// No ha encontrado nada con ese nombre
				System.out.println("No existe ninguna ubicación con ese nombre.");
				return;
			} else {
				System.out.println("La capacidad actual de la ubicación '" + rs.getString("nombre") + "' es: " + rs.getInt("capacidad"));
				
				// Bucle hasta que se introduzca un número válido
				do {
					System.out.println("Introduce la nueva capacidad máxima:");
					try {
						nuevaCapacidad = Integer.parseInt(scanner.nextLine());
						if( nuevaCapacidad <= 0 ) System.out.println("El número introducido debe ser mayor que 0.");
					} catch (NumberFormatException e) {
		                System.out.println("El número introducido no es válido.");
		            }
					
				} while ( nuevaCapacidad <= 0);
				
				// Se guarda la nueva capacidad
				s.executeUpdate("Update ubicaciones set capacidad = " + nuevaCapacidad + " where nombre = '" + nombreUbicacion + "'");
				
			}
			
			System.out.println("La nueva capacidad de la ubicación '" + nombreUbicacion + "' es: " + nuevaCapacidad);
			
		} catch (SQLException e) {
			muestraErrorSQL(e);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		finally {
			try {
				if( scanner != null ) scanner.close();
				if( rs !=null ) rs.close();
				if( s !=null ) s.close();
				if( c !=null ) c.close();
			} catch(Exception ex) {}
		}
        
        
        
	
	}

}
