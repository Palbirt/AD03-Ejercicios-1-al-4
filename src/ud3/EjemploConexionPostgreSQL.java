package ud3;

import java.sql.*;

public class EjemploConexionPostgreSQL {

	public static void main(String[] args) {
		
		try {
			Connection conexion = DriverManager.getConnection("jdbc:postgresql://127.0.0.1/test", "postgres", "postgres");
			
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
