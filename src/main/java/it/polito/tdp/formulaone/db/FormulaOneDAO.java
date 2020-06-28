package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Collegamento;
import it.polito.tdp.formulaone.model.LapTime;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> prendiAnni(){
		String sql = "SELECT DISTINCT s.year anni" + 
				" from seasons s" + 
				" ORDER BY s.year";
		List<Integer> lista = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Integer i= rs.getInt("anni");
				lista.add(i);
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere anni.", e);
			
		}
	}
	
	public List<Race> prendiGare(int anno){
		String sql = "SELECT r.raceId id, r.year anno, r.round giro, r.circuitId circ, r.name nome, r.date dat, r.time temp, r.url url" + 
				" FROM races r" + 
				" WHERE r.year = ? ";
		List<Race> lista = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				//Year a = (Year)rs.getInt("anno");
				int giro = rs.getInt("giro");
				int circ = rs.getInt("circ");
				String nome = rs.getString("nome");
				String url = rs.getString("url") ;
				
				Race i = new Race(id, null, giro, circ, nome, null, null, url);
				
				lista.add(i);
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere le gare.", e);
			
		}
	}

	public List<Collegamento> prendiCollegamento(int anno, Map<Integer, Race> mappa){
		String sql = "SELECT r1.raceId gara1, r2.raceId gara2, COUNT(DISTINCT r1.driverId) peso" + 
				" FROM results r1, results r2, drivers d1, drivers d2, races r, races ra2" + 
				" WHERE r.year = ? AND r1.raceId = r.raceId AND r2.raceId = ra2.raceId AND ra2.year = r.year" + 
				"		AND r1.statusId = 1 AND r2.statusId = 1" + 
				"		AND r1.raceId < r2.raceId" + 
				"		AND r1.driverId = d1.driverId" + 
				"		AND r2.driverId = d2.driverId" + 
				"		AND r1.driverId< r2.driverId" + 
				" GROUP BY r1.raceId";
		List<Collegamento> lista = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				if(mappa.containsKey(rs.getInt("gara1")) && mappa.containsKey(rs.getInt("gara2"))) {
					Race r1 = mappa.get(rs.getInt("gara1"));
					Race r2 = mappa.get(rs.getInt("gara2"));
					Integer peso = rs.getInt("peso");
					
					Collegamento col = new Collegamento(r1, r2, peso);
				
					lista.add(col);
				}
				
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere i collegamenti.", e);
		}
	}

	public List<LapTime> prendiGaraSimulazione(int garaId){
		String sql = "SELECT l.raceId gara, l.driverId pil, l.lap giro, l.position pos, l.time temp, l.milliseconds milli" + 
				" FROM laptimes l" + 
				" WHERE l.raceId = ? ";
		List<LapTime> lista = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, garaId);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				int gara = garaId;
				int dri = rs.getInt("pil");
				int giro = rs.getInt("giro");
				int pos = rs.getInt("pos");
				String temp = rs.getString("temp");
				int milli = rs.getInt("milli");
				
				LapTime l = new LapTime(gara, dri, giro, pos, temp, milli);
				
				lista.add(l);
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere i dati per la simulazione.", e);
		}
	}
}

