package it.polito.tdp.formulaone.model;

import java.time.LocalTime;

public class Evento {

	public enum EventType{
		PAUSA, CORSA
	}
	
	private LapTime partecipante;
	private EventType tipo;
	private LocalTime tempo;
	
	//, LocalTime tempo
	public Evento(LapTime partecipante, EventType tipo) {
		super();
		this.partecipante = partecipante;
		this.tipo = tipo;
		//this.tempo = tempo;
	}


	public LapTime getPartecipante() {
		return partecipante;
	}


	public void setPartecipante(LapTime partecipante) {
		this.partecipante = partecipante;
	}


	public EventType getTipo() {
		return tipo;
	}


	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}


	public LocalTime getTempo() {
		return tempo;
	}


	public void setTempo(LocalTime tempo) {
		this.tempo = tempo;
	}
	
	
	
}
