package pl.krasnowski.DigitalKitchen.model.domain.user;

public enum Intolerance {
    NONE("none"),
    DAIRY("dairy"),
    GLUTEN("gluten"),
    GRAINS("grains"),
    PEANUT("peanut"),
    SEAFOOD("sea food"),
    SESAME("sesame"),
    SHELLFISH("shellfish"),
    SOY("soy"),
    TREE_NUT("tree nut"),
    WHEAT("wheat");


    private final String name;

    Intolerance(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}