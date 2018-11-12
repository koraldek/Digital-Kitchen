package pl.krasnowski.DigitalKitchen.model.domain.food;


public enum Origin {
    ANIMAL("animal"),
    VEGETABLE("vegetable"),
    COMMON("common"),
    BRANDED("branded"),
    MEAT("meat");

    private final String name;

    Origin(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}