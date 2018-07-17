package pl.krasnowski.DigitalKitchen.model.domains;

import javax.persistence.Entity;

public enum BodyType {
	Ectomorph("Ectomomorph"),
	Endomorph("Endomorph"),
	Mesomorph("Mesomorph");

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param name
	 */
	BodyType(String name) {	}

}