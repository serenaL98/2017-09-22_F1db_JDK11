package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.formulaone.model.Evento.EventType;

public class Simulatore {

	//INPUT
	private double probabilita;
	private int secondi;
	
	//OUTPUT
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Evento> coda;
	
	//MODELLO DEL MONDO
	private Map<LapTime, Integer> pilotaPosizione;
	
	//STAMPA OUTPUT
	
	//SIMULAZIONE
	public void inizia(List<LapTime> pilotiGara, int T, float P) {
		//inizializzo
		this.coda = new PriorityQueue<>();
		this.probabilita = P;
		this.secondi = T;
		
		this.pilotaPosizione = new HashMap<>();
		
		//creo gli eventi
		for(LapTime l: pilotiGara) {
			this.coda.add(new Evento(l, EventType.CORSA));
		}
	}
	
	public void avvia() {
		while(!this.coda.isEmpty()) {
			Evento e = this.coda.poll();
			processEvent(e);
		}
	}
	
	public void processEvent(Evento e) {
		
		switch(e.getTipo()) {
		
		case CORSA:

			double prob = Math.random();
			
			if(prob<=this.probabilita) {
				//questo pilota a questo giro va in pausa
				this.coda.add(new Evento(e.getPartecipante(), EventType.PAUSA));
			}
			
			break;
			
		case PAUSA:
			//dopo T tempo torna al prossimo giro
			e.getPartecipante().setMiliseconds(secondi*1000);
			e.getPartecipante().setLap(e.getPartecipante().getLap()+1);
			this.coda.add(new Evento(e.getPartecipante(), EventType.CORSA));
			break;
		}
		
	}
	
}
