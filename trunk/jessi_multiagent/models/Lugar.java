package models;

import static misc.DebugFunctions.log;
import static misc.DebugFunctions.logError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Lugar extends Model {

	private int id;
	private int capacidad;
	private String tipo;
	private String codigo;
	private LinkedHashSet<Horario> horariosDisp;
	private static final LinkedHashSet<Horario> horariosDispGeneral = generaHorario();

	protected Lugar(int id, int capacidad, String tipo, String codigo)
			throws SQLException {
		this.id = id;
		this.capacidad = capacidad;
		this.tipo = tipo;
		this.codigo = codigo;
		this.horariosDisp = getHorario();
	}

	@Override
	public String toString() {
		return "Lugar(" + id + "," + capacidad + "," + tipo + "," + codigo
				+ horariosDisp + ")";
	}

	public int getId() {
		return id;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public String getTipo() {
		return tipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public LinkedHashSet<Horario> getHorariosDisp() {
		return horariosDisp;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id == ((Lugar) obj).id;
	}

	private LinkedHashSet<Horario> getHorario() throws SQLException {
		if (horariosDisp == null) {
			horariosDisp = new LinkedHashSet<Horario>();
			for (Horario h : horariosDispGeneral) {
				horariosDisp.add(new Horario(h.getId(), h.getdiaId(), h
						.getHoraInicio(), h.getHoraFin()));
			}
		}
		return horariosDisp;
	}

	// Funcion que regresa regresa un conjunto con todos los horarios indicados
	// en la base de datos.
	private static LinkedHashSet<Horario> generaHorario() {

		LinkedHashSet<Horario> horarioSet = new LinkedHashSet<Horario>();
		String query = "select * from horarios_disp";
		try {
			Connection c = Model.getDBConnection();
			PreparedStatement s = c.prepareStatement(query);
			ResultSet result = s.executeQuery();

			while (result.next()) {
				int id = result.getInt("Horario_Disp_Id");
				int diaId = result.getInt("Dia_Id");
				int horaInicio = result.getInt("Hora_Inicio");
				int horaFin = result.getInt("Hora_Fin");
				horarioSet.add(new Horario(id, diaId, horaInicio, horaFin));
			}
		} catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			horarioSet = null;
		}
		return horarioSet;
	}

	public static Lugar createLugarFromId(int id) throws SQLException {
		String query = "select * from lugar where Lugar_Id = " + id + ";";
		Lugar lugar = null;
		try {
			Connection c = Model.getDBConnection();
			PreparedStatement s = c.prepareStatement(query);
			ResultSet result = s.executeQuery();
			result.first();
			int capacidad = result.getInt("Capacidad");
			String tipo = result.getString("Tipo");
			String codigo = result.getString("Codigo");
			lugar = new Lugar(id, capacidad, tipo, codigo);
			log("Loading " + lugar.toString());
		} catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			throw new SQLException(e);
		}
		return lugar;
	}
	
	public static LinkedList<Lugar> createAll() throws SQLException {

		LinkedList<Lugar> professors = new LinkedList<Lugar>();
		String query = "select * from lugar";

		try {
			Connection c = Model.getDBConnection();
			PreparedStatement s = c.prepareStatement(query);
			ResultSet result = s.executeQuery();

			while (result.next()) {
				int id = result.getInt("Lugar_Id");
				int capacidad = result.getInt("Capacidad");
				String tipo = result.getString("Tipo");
				String codigo = result.getString("Codigo");
				Lugar l = new Lugar(id, capacidad, tipo, codigo);
				professors.add(l);
				log("Loading " + l.toString());
			}

		} catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			throw new SQLException(e);
		}
		return professors;
	}

	public static void main(String[] args) throws Exception {
		for (int i = 1; i < 100; i++) {
			createLugarFromId(i);
		}
	}
}