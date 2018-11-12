package pl.krasnowski.DigitalKitchen.model.domain.user;

public enum Silhouette {
    SKINNY("skinny", 0),
    FIT("fit", 1),
    NORMAL("normal", 2),
    THICKER("thicker", 3),
    SLIGHTLYMUSCULAR("slightly muscular", 4),
    CROSSFITTER("crossfitter", 5),
    BODYBUILDER("bodybuilder", 6),
    STRONGMAN("strongman", 7),
    POWERLIFTER("powerlifter", 8);


    private String name;
    private int mappedValue;

    public String getName() {
        return this.name;
    }

    public int getMappedValue() {
        return mappedValue;
    }


    Silhouette(String name, int mappedValue) {
        this.name = name;
        this.mappedValue = mappedValue;
    }

}
