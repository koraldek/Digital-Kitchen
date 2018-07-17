package pl.krasnowski.DigitalKitchen.model.domains;


public enum Unit {
    g("gram"),
    l("liter"),
    cup("cup"),
    spoon("spoon"),
    mg("miligram"),
    µg("microgram"),
    IU("internationalUnit"),
    kcal("kcal"),
    kJ("kJule"),
    slice("slice"),
    oz("ounce"),
    link("link");

    private String name;

    public String getName() {
        return this.name;
    }


    /**
     * @param name of unit
     */
    Unit(String name) {
    }

}