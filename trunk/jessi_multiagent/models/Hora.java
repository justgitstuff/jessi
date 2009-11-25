package models;

import static misc.DebugFunctions.log;
import static misc.DebugFunctions.logError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Read-only model for the Hora class.
 * Future Change: Change this to use Hibernate.
 */
// TODO: Not very pretty, this SHOULD be changed.
public class Hora extends Model {
	
	private int id;
	private int nombre; // Horario militar 
	
	protected Hora(int id,  int nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}
	
	public int getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return "Hora(" + id + "," + nombre + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.id == ((Hora) obj).id;
	}
	
	// Construye un objeto hora, dado el id y una conexion a la base de datos
	public static Hora createHora(int id) throws SQLException {
		String query = "select * from hora where Hora_Id = " + id + ";";
		Hora hora = null;
		try {
			Connection c = Model.getDBConnection();
			PreparedStatement s = c.prepareStatement(query);
			ResultSet result = s.executeQuery();
			result.first();
			int hora_nombre = result.getInt("Hora_Nombre");
			hora = new Hora(id, hora_nombre);
			log("Loading " + hora.toString());
		}
		catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			throw new SQLException(e);
		}
		return hora;
	}
}