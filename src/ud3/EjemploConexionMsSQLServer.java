package ud3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EjemploConexionMsSQLServer {

public static void main(String[] args) {
		
		try {
			Connection conexion = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=test;user=sa;password=#Nu3vaP@ssw0rd24;encrypt=true;trustServerCertificate=true");
			
			String sql = "select apellidos from clientes";
			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
			
			rs.close();
			sentencia.close();
			conexion.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}