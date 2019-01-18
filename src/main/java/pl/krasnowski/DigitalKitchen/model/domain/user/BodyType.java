package pl.krasnowski.DigitalKitchen.model.domain.user;

public enum BodyType {
    ECTOMORPH("Ectomomorph"),
    ENDOMORPH("Endomorph"),
    MESOMORPH("Mesomorph");

    private final String name;

    public String getName() {
        return this.name;
    }

    BodyType(String name) {
        this.name = name;
    }
}