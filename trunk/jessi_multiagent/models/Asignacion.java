package models;

import static misc.DebugFunctions.logError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
				query = "DROP TABLE IF EXISTS `itesm`.`asignacion`; " +
						"CREATE TABLE  `itesm`.`asignacion` ( " + 
						"`Grupo_Id` int(11) NOT NULL DEFAULT '0', " + 
						"`Profesor_Id` int(11) NOT NULL DEFAULT '0', " + 
						"`Lugar_Id` int(11) NOT NULL DEFAULT '0', " +
						"`Horario_Disp_Id` int(11) DEFAULT NULL, " + 
						"PRIMARY KEY (`Grupo_Id`,`Profesor_Id`,`Lugar_Id`), " + 
						"KEY `Profesor_Id` (`Profesor_Id`), " + 
						"KEY `Lugar_Id` (`Lugar_Id`), " + 
						"KEY `Horario_Disp_Id` (`Horario_Disp_Id`) " +
						") ENGINE=MyISAM DEFAULT CHARSET=latin1; ";
				
				PreparedStatement s = c.prepareStatement(query);
				s.executeQuery();
			}
			
			for(Asignacion a: asignaciones) {
				query = "INSERT INTO asignacion VALUES(" + a.getGrupoId() +
							   "," + a.getProf().getId() + "," + a.getLugar().getId() + 
							   "," + a.getHorario().getId() + ";";
				
				log("Executing query: " + query);
				PreparedStatement s = c.prepareStatement(query);
				s.executeQuery();
			}
		} catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
}