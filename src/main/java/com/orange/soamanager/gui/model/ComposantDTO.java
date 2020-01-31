package com.orange.soamanager.gui.model;

import java.util.List;

public class ComposantDTO {
private List<Composant>composant;
	
	//default and parameterized constructor
	public void addComposant(Composant composant) {
		this.composant.add(composant);
	}
	
     public ComposantDTO(List<Composant> composant) {
		super();
		this.composant = composant;
	}

	//getter and setter

	public List<Composant> getComposant() {
		return composant;
	}

	public void setComposant(List<Composant> composant) {
		this.composant = composant;
	}

}
