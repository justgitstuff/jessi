package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import static misc.DebugFunctions.*;

/**
 * Read-only model for the professor class.
 * Future Change: Change this to use Hibernate.
 */
public class Profesor extends Model {
	
	private int id;
	private String nombre;
	private String tipo;
	private LinkedHashSet<Horario> horarios;
	
	protected Profesor(int id, String nombre, String tipo) throws SQLException {
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.horarios = getHorario();
	}
		
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	private LinkedHashSet<Horario> getHorario() throws SQLException{
		if(horarios == null) {
			horarios = new LinkedHashSet<Horario>();
			String query = "select * from horarios_disp " +
						   "where Horario_Disp_Id in " +
						   "(select Horario_Disp_Id from profesor_horario " +
						   "where profesor_id = " + id +")";
			try {
				Connection c = Model.getDBConnection();
				PreparedStatement s = c.prepareStatement(query);
				ResultSet result = s.executeQuery();
				
				while(result.next()) {
					int id = result.getInt("Horario_Disp_Id");
					int diaId = result.getInt("Dia_Id");
					int horaInicio = result.getInt("Hora_Inicio");
					int horaFin = result.getInt("Hora_Fin");
					horarios.add(new Horario(id, diaId, horaInicio, horaFin));
				}
			} catch (SQLException e) {
				logError("SQL Statement failed: " + query);
				e.printStackTrace();
				throw new SQLException(e);
			}		
		}
		return horarios;
	}
	
	@Override
	public String toString() {
		return "Profesor("+id+","+nombre+","+tipo+","+horarios+")";
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.id == ((Profesor) obj).id;
	}
	
	/** Load a professor from the database given his id*/
	public static Profesor createProfesorFromId(int id) throws SQLException {
		String query = "select * from profesor where Profesor_Id = " + id + ";";
		Profesor prof = null;
		try {
			Connection c = Model.getDBConnection();
			PreparedStatement s = c.prepareStatement(query);
			ResultSet result = s.executeQuery();
			result.first();
			String nombre = result.getString("Profesor_Nombre");
			String tipo = result.getString("Profesor_Tipo");				
			prof = new Profesor(id, nombre, tipo);
			log("Loading " + prof.toString());
		}
		catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			throw new SQLException(e);
		}
		return prof;
	}
	
	/** Load all the professors from the database table */
	public static LinkedList<Profesor> loadProfessors() throws SQLException{
		
		LinkedList<Profesor> professors = new LinkedList<Profesor>();
		String query = "select * from profesor";
		
		try {
			Connection c = Model.getDBConnection();
			PreparedStatement s = c.prepareStatement(query);
			ResultSet result = s.executeQuery();
			
			while(result.next()) {
				int id = result.getInt("Profesor_Id");
				String nombre = result.getString("Profesor_Nombre");
				String tipo = result.getString("Profesor_Tipo");				
				Profesor p = new Profesor(id, nombre, tipo);
				professors.add(p);
				log("Loading " + p.toString());
			}
			
		} catch (SQLException e) {
			logError("SQL Statement failed: " + query);
			e.printStackTrace();
			throw new SQLException(e); 
		}		
		return professors;
	}
	
	public static void main(String[] args) throws Exception {
		for(int i = 1; i < 10; i++) {
			createProfesorFromId(i);
		}
	}
}