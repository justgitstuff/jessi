package models;

import static misc.DebugFunctions.log;
import static misc.DebugFunctions.logError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Lugar extends Model {
	
	private int id;
	private int capacidad;
	private String tipo;
	private String codigo;
	private LinkedList<Hora> horarios;
	
	protected Lugar(int id, int capacidad, String tipo, String codigo) {
		this.id = id;
		this.capacidad = capacidad;
		this.tipo = tipo;
		this.codigo = codigo;
	}
	
	@Override
	public String toString() {
		return "Lugar(" + id + "," + capacidad + "," + tipo + "," + codigo + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.id == ((Lugar) obj).id;
	}
	
	public static Lugar createLugar(int id) throws SQLException {
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
		}
		catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			throw new SQLException(e);
		}
		return lugar;
	}
	
	public static void main(String[] args) throws Exception {
		for(int i = 1; i < 100; i++) {
			createLugar(i);
		}
	}
}