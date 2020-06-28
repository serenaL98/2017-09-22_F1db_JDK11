package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {

	private FormulaOneDAO dao;
	private List<Season> stagioni;
	
	//grafo semplice, pesato, non orientato
	private Graph<Race, DefaultWeightedEdge> grafo;
	private List<Race> gare;
	private Map<Integer, Race> mappa;
	private List<Collegamento> collegamenti;
	
	public Model() {
		this.dao = new FormulaOneDAO();
		this.stagioni = this.dao.getAllSeasons();
		this.gare = new ArrayList<>();
		this.mappa = new HashMap<>();
		this.collegamenti = new ArrayList<>();
	}
	
	public List<Season> prendiStagioni(){
		return this.stagioni;
	}
	
	public void creaGrafo(int anno) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.gare = this.dao.prendiGare(anno);
		
		for(Race r: this.gare) {
			mappa.put(r.getRaceId(), r);
		}
		
		//VERTICI
		Graphs.addAllVertices(this.grafo, this.gare);
		System.out.println("#VERTICI: "+this.grafo.vertexSet().size());
		
		//ARCHI
		this.collegamenti = this.dao.prendiCollegamento(anno, mappa);
		
		for(Collegamento c: this.collegamenti) {
			Graphs.addEdge(this.grafo, c.getGara1(), c.getGara2(), c.getPeso());
		}
		System.out.println("#ARCHI: "+this.grafo.edgeSet().size());
	}
	
	public String calcolaPesoMax() {
		int max = 0;
		
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>=max) {
				max = (int) this.grafo.getEdgeWeight(e);
			}
		}
		
		String stampa = "";
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) == max) {
				stampa += this.grafo.getEdgeSource(e).getName()+" --- "+this.grafo.getEdgeTarget(e).getName()+", peso: "+max+"\n";
			}
		}
		return stampa;
	}
	
	//-------PUNTO 2-------
	public List<Race> prendiGare(){
		return this.gare;
	}
}
