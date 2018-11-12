package pl.krasnowski.DigitalKitchen.model.domain.food;


public enum Unit {
    g("gram"),
    l("liter"),
    cup("cup"),
    spoon("spoon"),
    mg("mili gram"),
    µg("micro gram"),
    IU("international Unit"),
    kcal("Kcal"),
    kJ("kilo jule"),
    slice("slice"),
    oz("ounce"),
    link("link");

    private final String name;

    Unit(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}