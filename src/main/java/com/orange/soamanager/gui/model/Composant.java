package com.orange.soamanager.gui.model;

public class Composant {
	
		private int id;
		private String nom;
		private String description;
		private String NomDuServeur;
		private String IpDuServeur;
		
	public Composant(int id, String nom, String description, String nomDuServeur, String ipDuServeur) {
		this.id = id;
		this.nom = nom;			                                           
		this.description = description;
		this.NomDuServeur = nomDuServeur;
		this.IpDuServeur = ipDuServeur;
		}
	
	public Composant( String nom, String description, String nomDuServeur, String ipDuServeur) {
		this.nom = nom;			                                           
		this.description = description;
		this.NomDuServeur = nomDuServeur;
		this.IpDuServeur = ipDuServeur;
		}
	
	public Composant() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNomDuServeur() {
		return NomDuServeur;
	}

	public void setNomDuServeur(String nomDuServeur) {
		NomDuServeur = nomDuServeur;
	}

	public String getIpDuServeur() {
		return IpDuServeur;
	}

	public void setIpDuServeur(String ipDuServeur) {
		IpDuServeur = ipDuServeur;
	}

	@Override
	public String toString() {
		return "Composant [id=" + id + ", nom=" + nom + ", description=" + description + ", NomDuServeur="
				+ NomDuServeur + ", IpDuServeur=" + IpDuServeur + "]";
	}
	
	
	}
	

