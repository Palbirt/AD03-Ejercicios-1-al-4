package evaluacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.CallableStatement;

public class Ejercicio4 {

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
		CallableStatement cs = null;
	
		Scanner scanner = new Scanner(System.in);
		int nEvento = 0; 
		
		 try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			s = c.createStatement();
				
			// Se muestra listado de eventos
			rs = s.executeQuery("SELECT * from eventos order by 1");
			
			System.out.println("Lista de eventos:");
			int i = 0;
			while ( rs.next() ) {
				System.out.println(rs.getInt("id_evento") + ". " + rs.getString("nombre_evento"));
				++i;
			}
			System.out.println("\n");
			
			// Se pide el número de evento hasta que sea uno válido
			do {
				//System.out.println("\n");
				System.out.println("Introduce el ID del evento para consultar la cantidad de asistentes:");
				
				try {
					nEvento = Integer.parseInt(scanner.nextLine());
					if( nEvento <= 0 || nEvento > i ) System.out.println("La opción introducida no existe.");
				} catch (NumberFormatException e) {
	                System.out.println("El número introducido no es válido.");
	            }
				
			} while ( nEvento < 1  || nEvento > i );
			
			// Con el ID buscamos el número de asistentes mediante el procedimiento almacenado
			cs = c.prepareCall("{call numero_asistentes(?,?)}");
			
			cs.setInt(1, nEvento);
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			
			cs.execute();
			
			int asistentes = cs.getInt(2);
			System.out.println("El número de asistentes para el evento seleccionado es: " + asistentes);
			
			
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
				if( cs !=null ) cs.close();
				if( c !=null ) c.close();
			} catch(Exception ex) {}
		}
		 
	}

}
