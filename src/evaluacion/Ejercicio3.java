package evaluacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio3 {

	

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
		String dni = "";
		Boolean dniValido = false;
		int nEvento = 0; 
		int plazasOcupadas = 0;
		ArrayList<Integer> ubicaciones = new ArrayList<>();
		String asistente = "";
	
		Scanner scanner = new Scanner(System.in);
		
		// Se pide el DNI en el formato correcto
		do {
			System.out.println("Introduce el DNI del asistente:");
	        dni = scanner.nextLine();
	        
	        if( !comprobarDNI(dni)  ) {
	        	System.out.println("El formato del DNI no es válido (9 números y 1 letra mayúscula).");
	        } else {
	        	dniValido = true;
	        }
		} while ( !dniValido );
        
        
        try {
			c = DriverManager.getConnection(urlConnection, user, pwd);
			s = c.createStatement();
			
			// Se busca el asistente en base al DNI
			rs = s.executeQuery("SELECT * from asistentes where dni = '" + dni + "'");
			
			// Si existe el DNI
			if( rs.next() ) {
				//  Mostrar el nombre correspondiente al DNI
				System.out.println("Estás realizando la reserva para: " + rs.getString("nombre"));
				asistente =  rs.getString("nombre");
			} else {
				// Si no existe, se pide nombre y se inserta nuevo asistente
				System.out.println("No se encontró un asistente con el DNI proporcionado.");
				System.out.println("Introduce el nombre del asistente:");
		        asistente = scanner.nextLine();
		        s.executeUpdate("Insert into asistentes values('" + dni + "', '" + asistente + "');" );
			}
			
			// Se muestra listado de eventos
			rs = s.executeQuery("SELECT * from eventos order by 1");
			
			System.out.println("Lista de eventos:");
			int i = 0;
			while ( rs.next() ) {
				System.out.println(rs.getInt("id_evento") + ". " + rs.getString("nombre_evento"));
				ubicaciones.add(rs.getInt("id_ubicacion")); // Añado la ubicación para tenerlas luego y no volver a llamar a la BBDD
				++i;
			}
			
			// Se pide el número de evento hasta que sea uno válido
			do {
				System.out.println("Elige el número de evento al que quiere asistir:");
				
				try {
					nEvento = Integer.parseInt(scanner.nextLine());
					if( nEvento <= 0 || nEvento > i ) System.out.println("La opción introducida no existe.");
				} catch (NumberFormatException e) {
	                System.out.println("El número introducido no es válido.");
	            }
				
			} while ( nEvento < 1  || nEvento > i );
			
			// Se comprueba que haya espacios libres en el evento
			// Primero hay que saber cuantas plazas están ocupadas
			rs = s.executeQuery("SELECT COUNT(dni) as 'ocupados' from asistentes_eventos where id_evento = " + nEvento);
			if( rs.next() ) {
				plazasOcupadas = rs.getInt("ocupados");
				// System.out.println("Número de asistentes confirmados: " + rs.getInt("ocupados"));
			}
			// Plazas totales del evento
			rs = s.executeQuery("SELECT * from ubicaciones where id_ubicacion = " + ubicaciones.get(nEvento - 1));
			if( rs.next() ) {
				
				// Si no quedan plazas se saca un mensaje y se acaba la app
				if( rs.getInt("capacidad") <= plazasOcupadas ) {
					System.out.println("No quedan plazas para ese evento.");
					return;
				}
				
			}
			
			// Si hemos llegado hasta aquí es que hay plazas para el evento y tenemos todos los datos
			
			// Guardamos en la tabla asistentes eventos
			s.executeUpdate("Insert into asistentes_eventos values('" + dni + "', " + nEvento + ");" );
			
			// Mostramos mensaje de registro
			System.out.println( asistente + " se ha registrado para el evento seleccionado.");
			
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
	
	
	public static void muestraErrorSQL(SQLException e) {
		System.err.println("SQL ERROR mensaje: " + e.getMessage());
		System.err.println("SQL Estado: " + e.getSQLState());
		System.err.println("SQL código específico: " + e.getErrorCode());
	}
	
	public static boolean comprobarDNI(String dni) {
        // Expresión regular: 8 dígitos seguidos de una letra mayúscula
        String patronDNI = "\\d{8}[A-Z]";
        
        // Comprobar si el formato coincide
        if (!dni.matches(patronDNI)) {
            return false;
        }

        return true;
    }

}
