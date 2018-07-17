package pl.krasnowski.DigitalKitchen.model.domains;

public enum Origin {
	ANIMAL("animal"),
	VEGETABLE("vegetable"),
	COMMON("common"),
	BRANDED("branded");

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
	Origin(String name) {	}


}