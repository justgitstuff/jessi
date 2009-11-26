package models;

import static misc.DebugFunctions.logError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import static misc.DebugFunctions.*;

public class Asignacion extends Model {

	private int grupoId;
	private Profesor prof;
	private Lugar lugar;
	private Horario horario;

	public Asignacion(int grupoId, Profesor prof, Lugar lugar, Horario horario) {
		this.grupoId = grupoId;
		this.prof = prof;
		this.lugar = lugar;
		this.horario = horario;
	}

	public int getGrupoId() {
		return grupoId;
	}

	public Profesor getProf() {
		return prof;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public Horario getHorario() {
		return horario;
	}

	@Override
	public String toString() {
		return "Asignacion(" + grupoId + "," + prof.getId() + ","
				+ lugar.getId() + "," + horario.getId() + ")";
	}

	public static void insertAsignaciones(LinkedList<Asignacion> asignaciones,
			boolean borrarTabla) throws SQLException {

		String query = "";
		try {
			Connection c = Model.getDBConnection();
			
			if (borrarTabla) {
				Statement st = c.createStatement();
				
				st.executeUpdate("DELETE FROM asignacion;");
			}
			
			for(Asignacion a: asignaciones) {
				query = "INSERT INTO asignacion VALUES(" + a.getGrupoId() +
							   "," + a.getProf().getId() + "," + a.getLugar().getId() + 
							   "," + a.getHorario().getId() + ");";
				
				log("Executing query: " + query);
				PreparedStatement s = c.prepareStatement(query);
				s.executeUpdate();
			}
		} catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
}