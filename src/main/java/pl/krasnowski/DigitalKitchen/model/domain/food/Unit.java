package pl.krasnowski.DigitalKitchen.model.domain.food;


public enum Unit {
    G("gram"),
    L("liter"),
    CUP("cup"),
    SPOON("spoon"),
    MG("mili gram"),
    µg("micro gram"),
    IU("international Unit"),
    KCAL("Kcal"),
    KJ("kilo jule"),
    SLICE("slice"),
    OZ("ounce"),
    PIECE("piece"),
    SERVING("serving"),
    SMALL("small"),
    MEDIUM("medium"),
    BIG("big"),
    LARGE("large"),
    JUMBO("jumbo"),
    EXTRA_LARGE("extra large"),
    BAR("bar"),
    CAN("can"),
    TABLESPOON("table spoon"),
    LINK("link");

    private final String name;

    Unit(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public static Unit getEnum(String value) {
        value = value.toLowerCase();
        for (Unit v : values()) {
            if (v.getName().equals(value)) return v;
        }
        try {
            return valueOf(value);
        } catch (IllegalArgumentException ignored) {
        }
        if (value.contains("grams") || value.equals("g")) return G;
        if (value.contains("pieces")) return PIECE;
        if (value.contains("serving")) return SERVING;
        if (value.contains("cup")) return CUP;
        if ((value.contains("table") && value.contains("spoon")) ||
                value.contains("tbsp")) return TABLESPOON;

        throw new IllegalArgumentException("Can't find enum of name:" + value);
    }
}