package it.polito.tdp.formulaone.model;

public class Collegamento {
	
	private Race gara1;
	private Race gara2;
	private Integer peso;
	
	
	public Collegamento(Race gara1, Race gara2, Integer peso) {
		super();
		this.gara1 = gara1;
		this.gara2 = gara2;
		this.peso = peso;
	}
	
	
	public Race getGara1() {
		return gara1;
	}
	public void setGara1(Race gara1) {
		this.gara1 = gara1;
	}
	public Race getGara2() {
		return gara2;
	}
	public void setGara2(Race gara2) {
		this.gara2 = gara2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}

}
