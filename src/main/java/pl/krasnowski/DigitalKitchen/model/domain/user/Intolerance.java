package pl.krasnowski.DigitalKitchen.model.domain.user;

public enum Intolerance {
    none("none"),
    dairy("dairy"),
    gluten("gluten"),
    grains("grains"),
    peanut("peanut"),
    seafood("seafood"),
    sesame("sesame"),
    shellfish("shellfish"),
    soy("soy"),
    treeNut("treeNut"),
    wheat("wheat");


    private final String name;

    Intolerance(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}