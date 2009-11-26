package models;

import static misc.DebugFunctions.log;
import static misc.DebugFunctions.logError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

/**
 * Read-only model for the Horario class. (Hora range) Future Change: Change
 * this to use Hibernate.
 */
// TODO: Not very pretty, this SHOULD be changed.

public class Horario extends Model {

	private int id;
	private int diaId;
	private int horaInicio;
	private int horaFin;
	private boolean busy;

	public Horario(int id, int diaId, int horaInicio, int horaFin) {
		assert horaInicio < horaFin;
		this.id = id;
		this.diaId = diaId;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.busy = false;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public int getDiaId() {
		return diaId;
	}

	public int getId() {
		return id;
	}

	public int getHoraInicio() {
		return horaInicio;
	}

	public int getHoraFin() {
		return horaFin;
	}

	public int getDuration() {
		assert horaFin - horaInicio > 0;
		return horaFin - horaInicio;
	}

	@Override
	public String toString() {
		return "Horario(" + id + "," + diaId + "," + horaInicio + "," + horaFin
				+ "," + busy + ")";
	}

	@Override
	public boolean equals(Object obj) {
		return this.id == ((Horario) obj).id;
	}

	@Override
	public int hashCode() {
		return new Integer(id).hashCode();
	}

	// Construye un objeto hora, dado el id y una conexion a la base de datos
	public static Horario createHorarioFromId(int id) throws SQLException {
		String query = "select * from horarios_disp where Horario_Disp_Id = "
				+ id + ";";
		Horario horario = null;
		try {
			Connection c = Model.getDBConnection();
			PreparedStatement s = c.prepareStatement(query);
			ResultSet result = s.executeQuery();
			result.first();
			int diaId = result.getInt("Dia_Id");
			int horaInicio = result.getInt("Hora_Inicio");
			int horaFin = result.getInt("Hora_Fin");
			horario = new Horario(id, diaId, horaInicio, horaFin);
			log("Loading " + horario.toString());
		} catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			throw new SQLException(e);
		}
		return horario;
	}
}