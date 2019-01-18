package pl.krasnowski.DigitalKitchen.model.domain.user;

public enum Sex {
    MALE("male"),
    FEMALE("female");

    private final String name;

    public String getName() {
        return this.name;
    }

    Sex(String name) {
        this.name = name;
    }


}
