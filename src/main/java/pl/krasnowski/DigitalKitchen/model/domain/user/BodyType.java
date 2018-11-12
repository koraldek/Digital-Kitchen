package pl.krasnowski.DigitalKitchen.model.domain.user;

public enum BodyType {
    Ectomorph("Ectomomorph", 0),
    Endomorph("Endomorph", 1),
    Mesomorph("Mesomorph", 2);

    private String name;
    private int mappedValue;

    public String getName() {
        return this.name;
    }

    public int getMappedValue() {
        return mappedValue;
    }


    BodyType(String name, int mappedValue) {
        this.name = name;
        this.mappedValue = mappedValue;
    }

    public static BodyType valueOf(int mappedValue) {
        switch (mappedValue) {
            case 0:
                return Ectomorph;
            case 1:
                return Endomorph;
            case 2:
                return Mesomorph;
            default:
                throw new IllegalArgumentException("Unknown mappedValue");
        }
    }
}