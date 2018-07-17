package pl.krasnowski.DigitalKitchen.model.domains;

import javax.persistence.*;


public enum Intolerance {
	none("none"),
	dairy("dairy"),
	gluten("gluten"),
	grains("grains"),
	peanut("peanut"),
	seafood("seafood"),
	sesame("sesame"),
	shellfish("shellfish"),
	soy("soy"),
	treeNut("treeNut"),
	wheat("wheat");


	private String name;

	public String getName() {
		return this.name;
	}

	/**
	 *
	 * @param name
	 */
	Intolerance(String name) {	}
}