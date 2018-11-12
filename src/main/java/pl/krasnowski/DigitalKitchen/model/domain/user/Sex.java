package pl.krasnowski.DigitalKitchen.model.domain.user;

public enum Sex {
    male("male", 0),
    female("female", 1);

    private final String name;
    private final int mappedValue;

    public String getName() {
        return this.name;
    }

    public int getMappedValue() {
        return mappedValue;
    }

    Sex(String name, int mappedValue) {
        this.name = name;
        this.mappedValue = mappedValue;
    }



}
